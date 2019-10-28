package com.tarjetaza.controller.claim;

import com.tarjetaza.service.ClaimService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public String users(Model model) {

        model.addAttribute("claims", claimService.findAll());

        return "claims/index";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {

        model.addAttribute("claim", claimService.findById(id));

        return "claims/detail";
    }

}
