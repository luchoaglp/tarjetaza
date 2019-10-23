package com.tarjetaza.controller.credit;

import com.tarjetaza.domain.Credit;
import com.tarjetaza.service.CreditService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/credits")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping
    public String credits(Model model) {

        List<Credit> credits  = creditService.findAllByOrderByIdAsc();;

        model.addAttribute("credits", credits);

        return "credits/index";
    }

}