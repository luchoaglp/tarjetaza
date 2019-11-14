package com.tarjetaza.service;

import com.tarjetaza.domain.ConsumptionFile;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConsumptionFileService {

    ConsumptionFile save(ConsumptionFile consumptionFile);

    List<ConsumptionFile> findAllByOrderByIdAsc();

    ConsumptionFile findById(Long id);

    Integer findConsumptionByDates(LocalDate dateFrom, LocalDate dateTo);
}
