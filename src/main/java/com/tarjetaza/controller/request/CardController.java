package com.tarjetaza.controller.request;

import com.tarjetaza.domain.Card;
import com.tarjetaza.domain.Request;
import com.tarjetaza.service.CardService;
import com.tarjetaza.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.tarjetaza.domain.RequestState.TARJETA_RECIBIDA;


@Controller
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;
    private final RequestService requestService;

    public CardController(CardService cardService, RequestService requestService) {
        this.cardService = cardService;
        this.requestService = requestService;
    }

    @GetMapping("/add/{requestId}")
    public String add(@PathVariable("requestId") Long requestId, Model model) {

        Card card = new Card();

        card.setEntidad("462");
        card.setSucursal("001");

        model.addAttribute("card", card);
        model.addAttribute("requestId", requestId);

        return "cards/add";
    }

    @PostMapping("/save")
    public String save(@Valid Card card, @RequestParam Long requestId) {

        Request request = requestService.findById(requestId);

        if(request.getCard() == null) {

            request.setRequestState(TARJETA_RECIBIDA);
            card.setRequest(request);

            cardService.save(card);
        }

        return "redirect:/requests/detail/" + requestId;
    }

}
