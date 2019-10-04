package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Credit;
import com.tarjetaza.repository.CreditRepository;
import com.tarjetaza.service.CreditService;
import org.springframework.stereotype.Service;

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
}
