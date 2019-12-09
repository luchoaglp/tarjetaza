package com.tarjetaza.service.impl;

import com.tarjetaza.domain.claim.Claim;
import com.tarjetaza.repository.ClaimRepository;
import com.tarjetaza.service.ClaimService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.tarjetaza.domain.claim.ClaimState.CERRADO;

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
    public List<Claim> findAllByOrderByIdDesc() {
        return claimRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Claim findById(Long id) {
        return claimRepository.findById(id).orElse(null);
    }

    @Override
    public Claim save(Claim claim) {
        return claimRepository.save(claim);
    }

    @Override
    public List<Claim> findOpenOrderByIdDesc() {
        return claimRepository.findOpenOrderByIdDesc();
    }

    @Override
    public Claim close(Claim claim) {

        Claim entity = findById(claim.getId());

        entity.setClaimDate(claim.getClaimDate());
        entity.setObservations(claim.getObservations().trim());
        entity.setClaimInFavorOf(claim.getClaimInFavorOf());
        entity.setClaimState(CERRADO);
        entity.setLastModifiedDate(LocalDateTime.now());

        return save(entity);
    }
}
