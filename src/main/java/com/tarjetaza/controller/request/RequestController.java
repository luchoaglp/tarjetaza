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

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public String requests(Model model) {

        model.addAttribute("requests", requestService.findAllByOrderByIdAsc());

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

}
