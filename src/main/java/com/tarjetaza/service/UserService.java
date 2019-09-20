package com.tarjetaza.service;

import com.tarjetaza.domain.security.User;

import java.util.List;

public interface UserService {

    boolean existsByUsername(String username);

    User save(User user);

    List<User> findAllByOrderByIdAsc();

    User findById(Long id);
}
