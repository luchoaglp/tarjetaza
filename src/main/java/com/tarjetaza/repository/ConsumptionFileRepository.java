package com.tarjetaza.repository;

import com.tarjetaza.domain.ConsumptionFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumptionFileRepository extends JpaRepository<ConsumptionFile, Long> {

    List<ConsumptionFile> findAllByOrderByIdAsc();

}
