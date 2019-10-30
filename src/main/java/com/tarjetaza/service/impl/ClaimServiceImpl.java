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

    @Override
    public Claim findById(Long id) {
        return claimRepository.findById(id).orElse(null);
    }

    @Override
    public Claim save(Claim claim) {
        return claimRepository.save(claim);
    }
}
