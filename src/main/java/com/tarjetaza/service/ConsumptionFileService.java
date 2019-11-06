package com.tarjetaza.service;

import com.tarjetaza.domain.ConsumptionFile;

import java.util.List;

public interface ConsumptionFileService {

    ConsumptionFile save(ConsumptionFile consumptionFile);

    List<ConsumptionFile> findAllByOrderByIdAsc();

    ConsumptionFile findById(Long id);
}
