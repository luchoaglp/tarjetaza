package com.tarjetaza.controller.request;

import com.tarjetaza.domain.Card;
import com.tarjetaza.domain.Request;
import com.tarjetaza.service.RequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/requests")
public class RequestController {

    @Value("${file.path}")
    private String path;

    private final RequestService requestService;
    private final JavaMailSender javaMailSender;

    public RequestController(RequestService requestService, JavaMailSender javaMailSender) {
        this.requestService = requestService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping
    public String requests(@RequestParam(required = false) String show,
                           Model model) {

        List<Request> requests = null;

        if(show == null) {
            requests = requestService.findInProcessOrderByIdDesc();
        } else if(show.equals("all")) {
            requests = requestService.findAllByOrderByIdDesc();
        }

        model.addAttribute("requests", requests);

        return "requests/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

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
    public String detail(@PathVariable("id") Long id, Model model) {

        model.addAttribute("request", requestService.findById(id));

        return "requests/detail";
    }

    @GetMapping("/change-status/{id}/{state}")
    public String changeStatus(@PathVariable("id") Long id,
                               @PathVariable("state") String state) {

        requestService.changeStatus(id, state);

        return "redirect:/requests/detail/" + id;
    }

    @GetMapping("/welcome-mail/{id}")
    public String welcomeMail(@PathVariable("id") Long id) {

        Request request = requestService.findById(id);
        Card card = request.getCard();

        LocalDate localDate = LocalDate.now();

        try {

            MimeMessage msg = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            msg.setFrom(new InternetAddress("altas@tarjetaza.com", false));

            //helper.setTo("lucho_aglp@hotmail.com");
            helper.setTo(request.getEmail());
            helper.setBcc("info@tarjetaza.com");
            //helper.setTo("soporte@cfsa.com.ar");
            //helper.setBcc("lucianogiannelli@cardlinesrl.com");
            //helper.setBcc("lucianogiannellicardline@gmail.com");

            helper.setSubject("Bienvenido " + request.getNombre() + " a Tarjetaza!");

            FileSystemResource file = new FileSystemResource(new File(path + "/img/mail-banner.jpg"));

            helper.addAttachment("mail-banner.jpg", file);

            helper.setText("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
                    "<head>" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" +
                    "<title>Tarjetaza</title>" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>" +
                    "</head>" +
                    "<body style=\"margin:0;padding:10px;\">" +
                    " <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"font-family:Arial, Helvetica,sans-serif;font-size:16px;color:#263238;border:7px solid #90C74B; border-radius:25px;\">" +
                    "<tr>" +
                    "<td style=\"padding:10px 10px 0 10px;text-align: center;\"><img src=\"cid:mail-banner.jpg\" alt=\"Tarjetaza\"></td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"padding:20px 3% 20px 0;text-align:right;\">" + localDate.format(DateTimeFormatter.ofPattern("d ' de '  MMMM ' de ' yyyy", new Locale("es", "AR"))) + "</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"padding:20px 0 10px 6%;\"><i>¡Bienvenido <b>" + request.getNombre() + "!</b></i></td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"padding:10px 0 20px 6%;\">Nos complace saber que ya sos parte de nuestra familia,</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"padding:60px 0;text-align:center;\"><span style=\" padding:12px; border:3px dashed #90C74B; border-radius: 10px;\"><b>Tu N° de cuenta es: " + card.getEntidad() + " " + card.getSucursal() + " " + card.getUsuario() + "</b></span></td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"padding:20px 0 10px 6%;\"><u><b>Registro de Usuario en Infotarjetas:</b></u></td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"padding:10px 2% 0 6%;\"><b>1. </b> Abrí en el explorador <a href=\"https://tarjetaza.com\" style=\"color: #263238;\" target=\"_blank\">www.tarjetaza.com</a>, aparecerá un menú con opciones, hace clic sobre <b>Ingresá</b></td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"padding:10px 3% 0 6%;\"><b>2. </b> Si ingresas por primera vez, tildá “Solicite una clave de Usuario”, se abrirá una ventana de registro de usuario, deberás completar los campos requeridos. Al finalizar la carga de datos, aparecerá el mensaje: “Se ha registrado con éxito! El administrador de sistema habilitara su cuenta a la brevedad”, tildá “Volver”</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"padding:10px 3% 50px 6%;\"><b>3. </b> Por ultimo realizá el logueo ingresando con tu número de <u>DNI o CUIT</u> y la <u>Contraseña</u> creada en el paso anterior, y comenzá a navegar por la plataforma.</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td style=\"padding:20px 3% 20px 0; text-align:right;\">Un cordial saludo, de todos los que hacemos <b>TARJETA<span style=\"color:#90C74B;\">ZA.</span></b></td>" +
                    "</tr>" +
                    "<tr><td style=\"padding: 28px\"><hr style=\"border:1px solid #263238;\"></td></tr>" +
                    "<tr>" +
                    "<td style=\"padding-bottom:32px;text-align:center;\">" +
                    "<span style=\"padding:3%\"> <a href=\"https://tarjetaza.com\" style=\"color: #263238;\" target=\"_blank\">www.tarjetaza.com</a> </span> &nbsp;&nbsp; " +
                    "<span style=\"padding:3%\"> <a style=\"color:#263238;\" href=\"mailto:info@tarjetaza.com\">info@tarjetaza.com</a> </span>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</body>" +
                    "</html>", true);

            javaMailSender.send(msg);

            return "redirect:/requests";

            //requestService.changeStatus(requests, "c_requested");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
