package com.tarjetaza.service.impl;

import com.tarjetaza.domain.DuplicateRequest;
import com.tarjetaza.repository.DuplicateRequestRepository;
import com.tarjetaza.service.DuplicateRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DuplicateRequestServiceImpl implements DuplicateRequestService {

    private final DuplicateRequestRepository duplicateRequestRepository;

    public DuplicateRequestServiceImpl(DuplicateRequestRepository duplicateRequestRepository) {
        this.duplicateRequestRepository = duplicateRequestRepository;
    }

    @Override
    public DuplicateRequest save(DuplicateRequest duplicateRequest) {
        return duplicateRequestRepository.save(duplicateRequest);
    }

    @Override
    public List<DuplicateRequest> findAllByOrderByIdAsc() {
        return duplicateRequestRepository.findAllByOrderByIdAsc();
    }

    @Override
    public DuplicateRequest findById(Long id) {
        return duplicateRequestRepository.findById(id).orElse(null);
    }
}
