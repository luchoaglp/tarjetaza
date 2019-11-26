package com.tarjetaza.controller.certificate;

import com.tarjetaza.domain.Certificate;
import com.tarjetaza.service.CertificateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }


    @GetMapping
    public String users(Model model) {

        model.addAttribute("certificates", certificateService.findAll());

        return "certificates/index";
    }

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("certificate", new Certificate());

        return "certificates/add";
    }

    @PostMapping("/save")
    public String save(@Valid Certificate certificate) {

        certificateService.save(certificate);

        return "redirect:/certificates";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        model.addAttribute("certificate", certificateService.findById(id));

        return "certificates/edit";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("certificate") Certificate certificate,
                         BindingResult result,
                         Model model) {

        if(result.hasErrors()) {

            model.addAttribute("certificate", certificate);

            return "certificates/edit";
        }

        certificateService.edit(certificate);

        return "redirect:/certificates";
    }

}
