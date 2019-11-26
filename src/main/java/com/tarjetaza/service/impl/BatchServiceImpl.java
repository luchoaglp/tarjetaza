package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Batch;
import com.tarjetaza.repository.BatchRepository;
import com.tarjetaza.service.BatchService;
import org.springframework.stereotype.Service;

@Service
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;

    public BatchServiceImpl(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    @Override
    public Batch save(Batch batch) {
        return batchRepository.save(batch);
    }
}
