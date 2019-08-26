package com.tarjetaza.controller.user;

import com.tarjetaza.service.WebRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/requests")
public class WebRequestController {

    private final WebRequestService webRequestService;

    public WebRequestController(WebRequestService webRequestService) {
        this.webRequestService = webRequestService;
    }

    @GetMapping
    public String requests(Model model) {

        model.addAttribute("requests", webRequestService.findAllByOrderByIdAsc());

        return "web-requests/index";
    }

    @GetMapping("/{id}")
    public String detail(Model model,
                         @PathVariable("id") Long id) {

        model.addAttribute("request", webRequestService.findById(id));

        return "web-requests/detail";
    }

}
