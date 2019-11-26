package com.tarjetaza.service;

import com.tarjetaza.domain.claim.ClaimSubject;

import java.util.List;

public interface ClaimSubjectService {

    void addAll(List<ClaimSubject> claimSubjects);

    ClaimSubject findById(Integer subjectId);

    List<ClaimSubject> findAll();
}
