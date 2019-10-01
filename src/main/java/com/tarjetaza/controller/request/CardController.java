package com.tarjetaza.controller.request;

import com.tarjetaza.domain.Card;
import com.tarjetaza.domain.Request;
import com.tarjetaza.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/cards")
public class CardController {

    private final RequestService requestService;

    public CardController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/add/{requestId}")
    public String add(@PathVariable("requestId") Long requestId, Model model) {

        Request request = requestService.findById(requestId);

        request.setCard(new Card());

        model.addAttribute("request", request);

        return "cards/add";
    }

}
