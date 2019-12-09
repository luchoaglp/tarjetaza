package com.tarjetaza.service;

import com.tarjetaza.domain.claim.Claim;

import java.util.List;

public interface ClaimService {

    List<Claim> findAll();

    List<Claim> findAllByOrderByIdDesc();

    Claim findById(Long id);

    Claim save(Claim claim);

    //List<Claim> findAllByOrderByIdAsc();

    List<Claim> findOpenOrderByIdDesc();

    Claim close(Claim claim);
}
