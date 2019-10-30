package com.tarjetaza.controller.claim;

import com.tarjetaza.domain.claim.Claim;
import com.tarjetaza.service.ClaimService;
import com.tarjetaza.service.ClaimSubjectService;
import com.tarjetaza.service.RequestService;
import com.tarjetaza.service.SubjectConceptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimService claimService;
    private final RequestService requestService;
    private final ClaimSubjectService claimSubjectService;
    private final SubjectConceptService subjectConceptService;

    public ClaimController(ClaimService claimService, RequestService requestService, ClaimSubjectService claimSubjectService, SubjectConceptService subjectConceptService) {
        this.claimService = claimService;
        this.requestService = requestService;
        this.claimSubjectService = claimSubjectService;
        this.subjectConceptService = subjectConceptService;
    }

    @GetMapping
    public String users(@RequestParam(required = false) String show, Model model) {

        List<Claim> claims = null;

        if(show == null) {
            claims = claimService.findOpenOrderByIdAsc();
        } else if(show.equals("all")) {
            claims = claimService.findAllByOrderByIdAsc();
        }

        model.addAttribute("claims", claims);

        return "claims/index";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {

        model.addAttribute("claim", claimService.findById(id));

        return "claims/detail";
    }

    @GetMapping("/client")
    public String selectClient(Model model) {

        model.addAttribute("requests", requestService.findWithCardOrderByIdAsc());

        return "claims/client";
    }

    @GetMapping("/add/{requestId}/{subjectId}")
    public String add(@PathVariable("requestId") Long requestId,
                      @PathVariable(value = "subjectId") Integer subjectId,
                      Model model) {

        model.addAttribute("subjects", claimSubjectService.findAll());
        model.addAttribute("concepts", claimSubjectService.findById(subjectId).getSubjectConcepts());
        model.addAttribute("request", requestService.findById(requestId));
        model.addAttribute("claim", new Claim());

        return "claims/add";
    }

    @PostMapping("/save")
    public String save(@Valid Claim claim,
                       @RequestParam Long requestId,
                       @RequestParam Integer conceptId) {

        claim.setRequest(requestService.findById(requestId));
        claim.setSubjectConcept(subjectConceptService.findById(conceptId));

        claim = claimService.save(claim);

        return "redirect:/claims/detail/" + claim.getId();
    }

}
