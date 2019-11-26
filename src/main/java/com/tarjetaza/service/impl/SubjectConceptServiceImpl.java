package com.tarjetaza.service.impl;

import com.tarjetaza.domain.claim.SubjectConcept;
import com.tarjetaza.repository.SubjectConceptRepository;
import com.tarjetaza.service.SubjectConceptService;
import org.springframework.stereotype.Service;

@Service
public class SubjectConceptServiceImpl implements SubjectConceptService {

    private final SubjectConceptRepository subjectConceptRepository;

    public SubjectConceptServiceImpl(SubjectConceptRepository subjectConceptRepository) {
        this.subjectConceptRepository = subjectConceptRepository;
    }

    @Override
    public SubjectConcept findById(Integer id) {
        return subjectConceptRepository.findById(id).orElse(null);
    }
}
