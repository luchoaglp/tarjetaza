package com.tarjetaza.repository;

import com.tarjetaza.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByRequestVirtualId(Long virtualId);
}
