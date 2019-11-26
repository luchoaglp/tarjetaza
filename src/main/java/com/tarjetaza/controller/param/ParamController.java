package com.tarjetaza.controller.param;

import com.tarjetaza.domain.Param;
import com.tarjetaza.service.ParamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("param", new Param());

        return "params/add";
    }

    @PostMapping("/save")
    public String save(@Valid Param param) {

        paramService.save(param);

        return "redirect:/params";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        model.addAttribute("param", paramService.findById(id));

        return "params/edit";
    }

    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("param") Param param,
                         BindingResult result,
                         Model model) {

        if(result.hasErrors()) {

            model.addAttribute("param", param);

            return "params/edit";
        }

        paramService.edit(param);

        return "redirect:/params";
    }

}
