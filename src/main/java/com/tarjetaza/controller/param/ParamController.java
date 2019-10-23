package com.tarjetaza.controller.param;

import com.tarjetaza.service.ParamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/params")
public class ParamController {

    private final ParamService paramService;

    public ParamController(ParamService paramService) {
        this.paramService = paramService;
    }

    @GetMapping
    public String users(Model model) {

        model.addAttribute("params", paramService.findAll());

        return "params/index";
    }

}
