package com.tarjetaza.repository;

import com.tarjetaza.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {


}
