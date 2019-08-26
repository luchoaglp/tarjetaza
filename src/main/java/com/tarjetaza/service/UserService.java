package com.tarjetaza.service;

import com.tarjetaza.domain.User;

public interface UserService {

    boolean existsByUsername(String username);

    User save(User user);
}
