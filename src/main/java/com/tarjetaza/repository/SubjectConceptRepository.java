package com.tarjetaza.repository;

import com.tarjetaza.domain.claim.SubjectConcept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectConceptRepository extends JpaRepository<SubjectConcept, Integer> {
}
