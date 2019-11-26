package com.tarjetaza.repository;

import com.tarjetaza.domain.claim.ClaimSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimSubjectRepository extends JpaRepository<ClaimSubject, Integer> {
}
