package com.tarjetaza.repository;

import com.tarjetaza.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {


}