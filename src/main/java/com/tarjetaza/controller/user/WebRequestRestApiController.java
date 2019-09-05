package com.tarjetaza.controller.user;

import com.tarjetaza.domain.WebRequest;
import com.tarjetaza.service.WebRequestService;
import com.tarjetaza.utils.MailFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
public class WebRequestRestApiController {

    @Value("${file.path}")
    private String path;

    private final WebRequestService webRequestService;
    private final JavaMailSender javaMailSender;

    public WebRequestRestApiController(WebRequestService webRequestService, JavaMailSender javaMailSender) {
        this.webRequestService = webRequestService;
        this.javaMailSender = javaMailSender;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid WebRequest webRequest,
                                                      BindingResult result) {

        Map<String, String> response = new HashMap<>();

        if(result.hasErrors()) {
            response.put("response", "Se ha producido un error en el envío de tus datos. Intentá nuevamente.");
        } else {

            webRequestService.save(webRequest);

            response.put("response", "Tus datos han sido guardados con éxito, en breve nos estaremos comunicando con vos.");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<Void> process(@RequestParam(value="requests[]") Long[] requests) {

        List<String> records = new ArrayList<>();

        //records.add("620046232        20190521                                                                                                                                                                                                                                                                                                                                                                                       ");
        records.add(MailFormat.formattedHeader());

        for(Long id : requests) {

            WebRequest request = webRequestService.findById(id);

            records.add(MailFormat.formattedBody(request));
        }
        //records.add("620146200100000000000000000000000T0127408691COLUNGA                       LUCIO IRAUL                   37                                      015251 A   B1902AXI03LA PLATA                 00000000000000010002740869100001000000000000000000020274086913000000000000000000000000000000000000        197905151M411D4100N  2740869136NO01SI010101 luciocolunga@cardlinesrl.com                                ");

        String fileName = "NU" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) +
                ".txt";

        try {

            Path file = Paths.get(path + fileName);

            Files.write(file, records, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo("info@tarjetaza.com");
            helper.setBcc("lucho_aglp@hotmail.com");
            helper.setSubject("Solicitudes de Altas");
            helper.setText("Solicitudes");

            FileSystemResource file = new FileSystemResource(new File(path + fileName));
            helper.addAttachment(fileName, file);

            javaMailSender.send(msg);

            webRequestService.changeStatus(requests, "c_requested");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        webRequestService.changeStatus(requests, "c_requested");

        return new ResponseEntity<>(HttpStatus.OK);

        //return "redirect:/";
    }

}
