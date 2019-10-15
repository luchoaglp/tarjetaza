package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Card;
import com.tarjetaza.repository.CardRepository;
import com.tarjetaza.service.CardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public List<Card> findByRequestVirtualId(Long virtualId) {
        return cardRepository.findByRequestVirtualId(virtualId);
    }
}
