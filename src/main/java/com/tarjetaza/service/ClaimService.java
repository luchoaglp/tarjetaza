package com.tarjetaza.service;

import com.tarjetaza.domain.claim.Claim;

import java.util.List;

public interface ClaimService {

    List<Claim> findAll();

    Claim findById(Long id);
}
