package com.tarjetaza.service;

import com.tarjetaza.domain.Request;

import java.util.List;

public interface RequestService {

    List<Request> findAll();
    List<Request> findInProcessOrderByIdAsc();
    List<Request> findAllByOrderByIdAsc();
    List<Request> findWithCardOrderByIdAsc();

    Request findById(Long id);

    Request save(Request request);

    void changeStatus(Long id, String op);
    void changeStatus(Long[] ids, String op);

    void edit(Request request);

    boolean existsByCuitCuil(String cuitCuil);
    boolean existsByVirtualId(Long virtualId);

    Request findByCuitCuil(String cuitCuil);
    Request findByVirtualId(Long virtualId);

    Request findByCardNumero(String numero);

    Integer findCountDeliveredCards();
}
