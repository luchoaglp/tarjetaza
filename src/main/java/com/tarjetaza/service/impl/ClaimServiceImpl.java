package com.tarjetaza.service.impl;

import com.tarjetaza.domain.claim.Claim;
import com.tarjetaza.repository.ClaimRepository;
import com.tarjetaza.service.ClaimService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    @Override
    public List<Claim> findAll() {
        return claimRepository.findAll();
    }
}
