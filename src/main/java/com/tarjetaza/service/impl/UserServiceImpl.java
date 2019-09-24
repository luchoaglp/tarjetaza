package com.tarjetaza.service.impl;

import com.tarjetaza.domain.security.User;
import com.tarjetaza.repository.UserRepository;
import com.tarjetaza.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllByOrderByIdAsc() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(User user) {

        User entity = findById(user.getId());

        entity.setFirstName(user.getFirstName().trim());
        entity.setLastName(user.getLastName().trim());
        entity.setEmail(user.getEmail().trim());
        entity.setLastModifiedDate(LocalDateTime.now());

        return save(entity);
    }

    @Override
    public User deactivate(Long id) {

        User entity = findById(id);

        entity.setActive(false);
        entity.setLastModifiedDate(LocalDateTime.now());

        return save(entity);
    }

    @Override
    public User findByUsernameAndActiveTrue(String username) {
        return userRepository.findByUsernameAndActiveTrue(username).orElse(null);
    }

    @Override
    public User updatePassword(User user) {

        User entity = findById(user.getId());

        entity.setPassword(user.getPassword());
        entity.setLastModifiedDate(LocalDateTime.now());

        return save(entity);
    }

}
