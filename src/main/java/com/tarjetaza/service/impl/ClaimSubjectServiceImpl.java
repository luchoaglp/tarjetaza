package com.tarjetaza.service.impl;

import com.tarjetaza.domain.claim.ClaimSubject;
import com.tarjetaza.repository.ClaimSubjectRepository;
import com.tarjetaza.service.ClaimSubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimSubjectServiceImpl implements ClaimSubjectService {

    private final ClaimSubjectRepository claimSubjectRepository;

    public ClaimSubjectServiceImpl(ClaimSubjectRepository claimSubjectRepository) {
        this.claimSubjectRepository = claimSubjectRepository;
    }

    public void addAll(List<ClaimSubject> claimSubjects) {
        claimSubjects.addAll(claimSubjects);
    }
}
