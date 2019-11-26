package com.tarjetaza.service;

import com.tarjetaza.domain.Card;

import java.util.List;

public interface CardService {

    Card save(Card card);

    List<Card> findByRequestVirtualId(Long virtualId);

    Card edit(Card enity, Card card);
}
