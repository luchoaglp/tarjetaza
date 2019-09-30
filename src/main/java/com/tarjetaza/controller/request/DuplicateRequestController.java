package com.tarjetaza.controller.request;

import com.tarjetaza.service.DuplicateRequestService;
import com.tarjetaza.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/duplicate-requests")
public class DuplicateRequestController {

    private final DuplicateRequestService duplicateRequestService;

    public DuplicateRequestController(DuplicateRequestService duplicateRequestService) {
        this.duplicateRequestService = duplicateRequestService;
    }

    @GetMapping
    public String requests(Model model) {

        model.addAttribute("duplicateRequests", duplicateRequestService.findAllByOrderByIdAsc());

        return "duplicate-requests/index";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {

        model.addAttribute("duplicateRequest", duplicateRequestService.findById(id));

        return "duplicate-requests/detail";
    }

}
