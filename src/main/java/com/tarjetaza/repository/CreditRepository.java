package com.tarjetaza.repository;

import com.tarjetaza.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    List<Credit> findAllByOrderByIdDesc();

    @Query("FROM Credit c WHERE c.creditState = 0 ORDER BY c.id DESC")
    List<Credit> findActiveOrderByIdDesc();
}
