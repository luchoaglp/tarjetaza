package com.tarjetaza.repository;

import com.tarjetaza.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findAllByOrderByIdAsc();

}
