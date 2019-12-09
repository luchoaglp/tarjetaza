package com.tarjetaza.repository;

import com.tarjetaza.domain.claim.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findAllByOrderByIdAsc();

    @Query("FROM Claim c WHERE c.claimState = 0 ORDER BY c.id DESC")
    List<Claim> findOpenOrderByIdDesc();

    List<Claim> findAllByOrderByIdDesc();
}
