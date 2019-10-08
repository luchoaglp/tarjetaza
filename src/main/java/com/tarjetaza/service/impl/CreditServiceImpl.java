package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Credit;
import com.tarjetaza.repository.CreditRepository;
import com.tarjetaza.service.CreditService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    public CreditServiceImpl(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Override
    public Credit save(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public List<Credit> findAllByOrderByIdAsc() {
        return creditRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Credit findById(Long id) {
        return creditRepository.findById(id).orElse(null);
    }

    @Override
    public Credit update(Credit credit) {

        credit.setLastModifiedDate(LocalDateTime.now());

        return save(credit);
    }

}
