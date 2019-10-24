package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Param;
import com.tarjetaza.repository.ParamRepository;
import com.tarjetaza.service.ParamService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public Param save(Param param) {
        return paramRepository.save(param);
    }

    @Override
    public Param findById(Long id) {
        return paramRepository.findById(id).orElse(null);
    }

    @Override
    public Param edit(Param param) {

        Param entity = findById(param.getId());

        entity.setDescription(param.getDescription().trim());
        entity.setValue(param.getDescription().trim());
        entity.setParamDate(param.getParamDate());
        entity.setLastModifiedDate(LocalDateTime.now());

        paramRepository.save(entity);

        return entity;
    }
}
