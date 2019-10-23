package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Credit;
import com.tarjetaza.domain.CreditState;
import com.tarjetaza.repository.CreditRepository;
import com.tarjetaza.service.CreditService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.tarjetaza.domain.CreditState.ACREDITADO;

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

    @Override
    public List<Credit> findActiveOrderByIdAsc() {
        return creditRepository.findActiveOrderByIdAsc();
    }

    @Override
    public void changeStatus(Long[] ids, String op) {

        for(Long id : ids) {

            Credit credit = findById(id);

            credit.setCreditState(getCreditState(op));
            credit.setLastModifiedDate(LocalDateTime.now());

            creditRepository.save(credit);
        }
    }

    private CreditState getCreditState(String op) {

        switch (op) {
            case "accredited":
                return ACREDITADO;
        }

        return null;
    }

}
