package com.tarjetaza.service;

import com.tarjetaza.domain.Request;

import java.util.List;

public interface RequestService {

    List<Request> findAll();
    List<Request> findActiveOrderByIdAsc();

    Request findById(Long id);

    List<Request> findAllByOrderByIdAsc();

    Request save(Request request);

    void changeStatus(Long id, String op);
    void changeStatus(Long[] ids, String op);

    void edit(Request request);

    boolean existsByCuitCuil(String cuitCuil);

    Request findByCuitCuil(String cuitCuil);

}
