package com.tarjetaza.controller.credit;

import com.tarjetaza.domain.Batch;
import com.tarjetaza.domain.Credit;
import com.tarjetaza.domain.Request;
import com.tarjetaza.payload.CreditRequest;
import com.tarjetaza.payload.CreditResponse;
import com.tarjetaza.service.BatchService;
import com.tarjetaza.service.CreditService;
import com.tarjetaza.service.RequestService;
import com.tarjetaza.utility.CreditMailFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/credits")
public class CreditRestController {

    @Value("${file.path}")
    private String path;

    private final CreditService creditService;
    private final RequestService requestService;
    private final BatchService batchService;
    private final JavaMailSender javaMailSender;

    public CreditRestController(CreditService creditService, RequestService requestService, BatchService batchService, JavaMailSender javaMailSender) {
        this.creditService = creditService;
        this.requestService = requestService;
        this.batchService = batchService;
        this.javaMailSender = javaMailSender;
    }

    @PostMapping
    public ResponseEntity<CreditResponse> create(@Valid @RequestBody CreditRequest creditRequest) {

        Request request = requestService.findByCardNumero(creditRequest.getNumero());

        if(request == null) {
            return new ResponseEntity<>(new CreditResponse("Tarjeta inexistente"), HttpStatus.NOT_FOUND);
        }

        Credit credit = creditService.save(new Credit(creditRequest.getAmount(), request));

        new Thread(() -> {

            SimpleMailMessage msg = new SimpleMailMessage();

            msg.setFrom("altas@tarjetaza.com");
            //msg.setTo("lucho_aglp@hotmail.com");
            msg.setTo("info@tarjetaza.com");
            msg.setSubject("Proceso: [Crédito] – Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            msg.setText(request.getApellido() + " " + request.getNombre() + ": $" + credit.getAmount());

            javaMailSender.send(msg);

        }).start();

        return new ResponseEntity<>(new CreditResponse(credit.getId(), "OK"), HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<Void> process(@RequestParam(value="credits[]") Long[] credits) {

        Batch batch = batchService.save(new Batch());

        List<String> records = new ArrayList<>();

        int total = 0;

        records.add(CreditMailFormat.formattedHeader(batch.getId()));
        // records.add("1000001          20190624462           PAGO VIRTUAL DEL SUR                     ");

        for(int i = 0; i < credits.length; i++) {

            Credit credit = creditService.findById(credits[i]);

            total += credit.getAmount();

            records.add(CreditMailFormat.formattedBody(batch.getId(), (i + 1), credit));

            credit.setBatch(batch);

            creditService.update(credit);
        }

        // records.add("20000010000000001201906244620017010017   010000000001000000      6300        000");

        char c = (char) (65 + batch.getId() % 26);

        String fileName = "COB" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + c +
                ".txt";

        records.add(CreditMailFormat.formattedFooter(batch.getId(), credits.length, total));
        // records.add("9000001          201906244620000000012000000010800000                           ");

        /*
        records
                .stream()
                .forEach(record -> System.out.println(record + "; " + record.length()));
        */

        try {

            Path file = Paths.get(path + fileName);

            Files.write(file, records, StandardCharsets.UTF_8);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {

            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            msg.setFrom(new InternetAddress("altas@tarjetaza.com", false));

            //helper.setTo("lucho_aglp@hotmail.com");
            helper.setTo("soporte@cfsa.com.ar");
            helper.setBcc("info@tarjetaza.com");

            helper.setSubject("Entidad: 462 – Proceso: [Cobranzas] – Fecha de Presentación: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            helper.setText("Entidad: 462\nPresentación: " +
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                    "Casos: " + credits.length + "\n" +
                    "Importe: " + total + ".00");

            FileSystemResource file = new FileSystemResource(new File(path + fileName));
            helper.addAttachment(fileName, file);

            javaMailSender.send(msg);

            creditService.changeStatus(credits, "accredited");

        } catch (MessagingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
