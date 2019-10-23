package com.tarjetaza.service;

import com.tarjetaza.domain.Credit;

import java.util.List;

public interface CreditService {

    Credit save(Credit credit);

    List<Credit> findAllByOrderByIdAsc();

    Credit findById(Long id);

    Credit update(Credit credit);

    List<Credit> findActiveOrderByIdAsc();

    void changeStatus(Long[] ids, String op);
}
