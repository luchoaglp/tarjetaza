package com.tarjetaza.controller.credit;

import com.tarjetaza.domain.Credit;
import com.tarjetaza.service.CreditService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/credits")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping
    public String credits(@RequestParam(required = false) String show,
                          Model model) {

        List<Credit> credits = null;

        if(show == null) {
            credits = creditService.findActiveOrderByIdDesc();
        } else if(show.equals("all")) {
            credits = creditService.findAllByOrderByIdDesc();
        }

        model.addAttribute("credits", credits);

        return "credits/index";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {

        model.addAttribute("credit", creditService.findById(id));

        return "credits/detail";
    }

}
