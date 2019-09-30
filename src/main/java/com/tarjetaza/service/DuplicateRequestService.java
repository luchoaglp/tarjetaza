package com.tarjetaza.service;

import com.tarjetaza.domain.DuplicateRequest;

import java.util.List;

public interface DuplicateRequestService {

    DuplicateRequest save(DuplicateRequest duplicateRequest);

    List<DuplicateRequest> findAllByOrderByIdAsc();

    DuplicateRequest findById(Long id);
}
