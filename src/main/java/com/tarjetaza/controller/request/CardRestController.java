package com.tarjetaza.controller.request;

import com.tarjetaza.domain.Card;
import com.tarjetaza.payload.CardsResponse;
import com.tarjetaza.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.tarjetaza.domain.RequestState.TARJETA_ENTREGADA;

@RestController
@RequestMapping("/api/cards")
public class CardRestController {

    private final CardService cardService;

    public CardRestController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{virtualId}")
    public ResponseEntity<CardsResponse> cardsByVirtualId(@PathVariable("virtualId") Long virtualId) {

        List<Card> cards = cardService.findByRequestVirtualId(virtualId);

        if(cards.isEmpty()) {
            return new ResponseEntity<>(new CardsResponse("El cliente no tiene tarjetas asignadas o no tiene un id asociado."), HttpStatus.NOT_FOUND);
        }

        CardsResponse cardsResponse = new CardsResponse("OK");

        cardsResponse.setCards(cards
                .stream()
                .filter(card -> card.getRequest().getRequestState() == TARJETA_ENTREGADA)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(cardsResponse, HttpStatus.OK);
    }

}
