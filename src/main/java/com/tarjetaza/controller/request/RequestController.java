package com.tarjetaza.controller.request;

import com.tarjetaza.domain.Request;
import com.tarjetaza.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public String requests(@RequestParam(required = false) String show,
                           Model model) {

        List<Request> requests = null;

        if(show == null) {
            requests = requestService.findInProcessOrderByIdAsc();
        } else if(show.equals("all")) {
            requests = requestService.findAllByOrderByIdAsc();
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

        /*
        } else if(request.getVirtualId() != null && requestService.existsByVirtualId(request.getVirtualId())) {

            model.addAttribute("request", request);

            result.addError(new FieldError(
                    "request",
                    "virtualId",
                    "El Virtual ID ya se encuentra registrado"
            ));

            return "requests/edit";
        }
        */

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

        return "redirect:/requests/detail/" + id;
    }

}
