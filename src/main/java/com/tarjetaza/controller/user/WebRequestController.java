package com.tarjetaza.controller.user;

import com.tarjetaza.service.WebRequestService;
import com.tarjetaza.utils.MailFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

@Controller
@RequestMapping("/requests")
public class WebRequestController {

    @Value("${file.path}")
    private String path;

    private final WebRequestService webRequestService;
    private final JavaMailSender javaMailSender;

    public WebRequestController(WebRequestService webRequestService, JavaMailSender javaMailSender) {
        this.webRequestService = webRequestService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping
    public String requests(Model model) {

        model.addAttribute("requests", webRequestService.findAllByOrderByIdAsc());

        return "web-requests/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {

        model.addAttribute("request", webRequestService.findById(id));

        return "web-requests/edit";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {

        model.addAttribute("request", webRequestService.findById(id));

        return "web-requests/detail";
    }

    @GetMapping("/change-status/{id}/{state}")
    public String changeStatus(@PathVariable("id") Long id,
                               @PathVariable("state") String state) {

        webRequestService.changeStatus(id, state);

        return "redirect:/requests";
    }

    @PostMapping("/process")
    public String process(@RequestParam(value="requests[]") Long[] requests) {

        List<String> records = new ArrayList<>();

        //records.add("620046232        20190521                                                                                                                                                                                                                                                                                                                                                                                       ");
        records.add(MailFormat.formattedHeader());

        for(Long id : requests) {
            records.add(MailFormat.formattedBody(webRequestService.findById(id)));
        }
        //records.add("620146200100000000000000000000000T0127408691COLUNGA                       LUCIO IRAUL                   37                                      015251 A   B1902AXI03LA PLATA                 00000000000000010002740869100001000000000000000000020274086913000000000000000000000000000000000000        197905151M411D4100N  2740869136NO01SI010101 luciocolunga@cardlinesrl.com                                ");

        String fileName = path +
                "NU" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) +
                ".txt";

        try {

            Path file = Paths.get(fileName);

            Files.write(file, records, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo("lucho_aglp@hotmail.com");
            helper.setSubject("Solicitudes de Altas");
            helper.setText("Solicitudes");

            FileSystemResource file = new FileSystemResource(new File(fileName));
            helper.addAttachment(fileName, file);

            javaMailSender.send(msg);

        } catch (MessagingException e) {
            e.printStackTrace();
        }


        /*
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo("lucho_aglp@hotmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
        */

        return "web-requests/index";
    }

}
