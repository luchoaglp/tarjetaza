package com.tarjetaza.service.impl;

import com.tarjetaza.domain.ConsumptionFile;
import com.tarjetaza.repository.ConsumptionFileRepository;
import com.tarjetaza.service.ConsumptionFileService;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionFileServiceImpl implements ConsumptionFileService {

    private final ConsumptionFileRepository consumptionFileRepository;

    public ConsumptionFileServiceImpl(ConsumptionFileRepository consumptionFileRepository) {
        this.consumptionFileRepository = consumptionFileRepository;
    }

    @Override
    public ConsumptionFile save(ConsumptionFile consumptionFile) {
        return consumptionFileRepository.save(consumptionFile);
    }
}
