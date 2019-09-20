package com.tarjetaza.service;

import com.tarjetaza.domain.security.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Integer roleId);

    List<Role> findAllById(List<Integer> ids);
}
