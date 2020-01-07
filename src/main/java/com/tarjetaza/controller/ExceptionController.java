package com.tarjetaza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exceptions")
public class ExceptionController {

    @GetMapping("/403")
    public String accessDenied() {
        return "exceptions/403";
    }

}
