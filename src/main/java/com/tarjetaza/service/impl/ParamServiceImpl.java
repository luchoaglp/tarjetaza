package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Param;
import com.tarjetaza.repository.ParamRepository;
import com.tarjetaza.service.ParamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParamServiceImpl implements ParamService {

    private final ParamRepository paramRepository;

    public ParamServiceImpl(ParamRepository paramRepository) {
        this.paramRepository = paramRepository;
    }

    @Override
    public List<Param> findAll() {
        return paramRepository.findAll();
    }
}
