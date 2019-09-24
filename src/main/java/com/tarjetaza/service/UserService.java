package com.tarjetaza.service;

import com.tarjetaza.domain.security.User;

import java.util.List;

public interface UserService {

    boolean existsByUsername(String username);

    User save(User user);

    List<User> findAllByOrderByIdAsc();

    User findById(Long id);

    User update(User user);

    User deactivate(Long id);

    User findByUsernameAndActiveTrue(String username);

    User updatePassword(User user);
}
