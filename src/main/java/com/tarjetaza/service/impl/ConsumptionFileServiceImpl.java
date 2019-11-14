package com.tarjetaza.service.impl;

import com.tarjetaza.domain.ConsumptionFile;
import com.tarjetaza.repository.ConsumptionFileRepository;
import com.tarjetaza.service.ConsumptionFileService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<ConsumptionFile> findAllByOrderByIdAsc() {
        return consumptionFileRepository.findAllByOrderByIdAsc();
    }

    @Override
    public ConsumptionFile findById(Long id) {
        return consumptionFileRepository.findById(id).orElse(null);
    }

    @Override
    public Integer findConsumptionByDates(LocalDate dateFrom, LocalDate dateTo) {
        return consumptionFileRepository.findConsumptionByDates(dateFrom, dateTo);
    }
}
