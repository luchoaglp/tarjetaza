package com.tarjetaza.service;

import com.tarjetaza.domain.Param;

import java.util.List;

public interface ParamService {

    List<Param> findAll();

    Param save(Param param);

    Param findById(Long id);

    Param edit(Param param);
}
