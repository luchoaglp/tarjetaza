package com.tarjetaza.repository;

import com.tarjetaza.domain.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Integer> {
}
