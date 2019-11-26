package com.tarjetaza.service;

import com.tarjetaza.domain.claim.Claim;

import java.util.List;

public interface ClaimService {

    List<Claim> findAll();

    Claim findById(Long id);

    Claim save(Claim claim);

    List<Claim> findAllByOrderByIdAsc();

    List<Claim> findOpenOrderByIdAsc();

    Claim close(Claim claim);
}
