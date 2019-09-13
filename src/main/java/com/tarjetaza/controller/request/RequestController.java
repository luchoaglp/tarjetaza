package com.tarjetaza.controller.request;

import com.tarjetaza.domain.Request;
import com.tarjetaza.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;
    //private final JavaMailSender javaMailSender;

    public RequestController(RequestService requestService/*, JavaMailSender javaMailSender*/) {
        this.requestService = requestService;
        //this.javaMailSender = javaMailSender;
    }

    @GetMapping
    public String requests(Model model) {

        model.addAttribute("requests", requestService.findAllByOrderByIdAsc());

        return "requests/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {

        model.addAttribute("request", requestService.findById(id));

        return "requests/edit";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("request") Request request,
                         BindingResult result,
                         Model model) {

        if(result.hasErrors()) {

            model.addAttribute("request", request);

            return "requests/edit";
        }

        requestService.edit(request);

        return "redirect:/requests/detail/" + request.getId();
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {

        model.addAttribute("request", requestService.findById(id));

        return "requests/detail";
    }

    @GetMapping("/change-status/{id}/{state}")
    public String changeStatus(@PathVariable("id") Long id,
                               @PathVariable("state") String state) {

        requestService.changeStatus(id, state);

        return "redirect:/requests";
    }

    /*
    @PostMapping("/process")
    public void process(@RequestParam(value="requests[]") Long[] requests) {

        List<String> records = new ArrayList<>();

        //records.add("620046232        20190521                                                                                                                                                                                                                                                                                                                                                                                       ");
        records.add(MailFormat.formattedHeader());

        for(Long id : requests) {

            Request request = requestService.findById(id);

            records.add(MailFormat.formattedBody(request));
        }
        //records.add("620146200100000000000000000000000T0127408691COLUNGA                       LUCIO IRAUL                   37                                      015251 A   B1902AXI03LA PLATA                 00000000000000010002740869100001000000000000000000020274086913000000000000000000000000000000000000        197905151M411D4100N  2740869136NO01SI010101 luciocolunga@cardlinesrl.com                                ");

        String fileName = "NU" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")) +
                ".txt";

        try {

            Path file1 = Paths.get(path + fileName);

            Files.write(file1, records, StandardCharsets.UTF_8);

            try {

                MimeMessage msg = javaMailSender.createMimeMessage();

                MimeMessageHelper helper = new MimeMessageHelper(msg, true);

                helper.setTo("info@tarjetaza.com");
                helper.setBcc("lucho_aglp@hotmail.com");
                helper.setSubject("Solicitudes de Altas");
                helper.setText("Solicitudes");

                FileSystemResource file2 = new FileSystemResource(new File(path + fileName));
                helper.addAttachment(fileName, file2);

                javaMailSender.send(msg);

                requestService.changeStatus(requests, "c_requested");

                return "redirect:/requests";

            } catch (MessagingException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        requestService.changeStatus(requests, "c_requested");

        //return "redirect:/";
    }
    */

}
