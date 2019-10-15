package com.tarjetaza.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tarjetaza.domain.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardsResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Card> cards;

    private String message;

    public CardsResponse(String message) {
        this.message = message;
    }

    public CardsResponse(List<Card> cards, String message) {
        this.cards = cards;
        this.message = message;
    }
}
