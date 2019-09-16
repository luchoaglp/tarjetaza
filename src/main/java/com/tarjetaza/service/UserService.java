package com.tarjetaza.service;

import com.tarjetaza.domain.security.User;

public interface UserService {

    boolean existsByUsername(String username);

    User save(User user);
}
