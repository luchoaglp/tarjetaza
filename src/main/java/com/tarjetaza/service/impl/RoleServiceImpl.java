package com.tarjetaza.service.impl;

import com.tarjetaza.domain.security.Role;
import com.tarjetaza.repository.RoleRepository;
import com.tarjetaza.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Integer roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    @Override
    public List<Role> findAllById(List<Integer> ids) {
        return roleRepository.findAllById(ids);
    }
}
