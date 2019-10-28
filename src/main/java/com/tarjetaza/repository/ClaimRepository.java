package com.tarjetaza.repository;

import com.tarjetaza.domain.claim.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
}
