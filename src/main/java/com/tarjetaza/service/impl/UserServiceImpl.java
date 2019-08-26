package com.tarjetaza.service.impl;

import com.tarjetaza.domain.User;
import com.tarjetaza.repository.UserRepository;
import com.tarjetaza.service.UserService;
import org.springframework.stereotype.Service;


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


    /*
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client getById(Long id) {
        return clientRepository.findById(id).get();
    }

    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }


    public boolean existsByDni(String dni) {
        return clientRepository.existsByDni(dni);
    }

    public void editData(Client client, Long clientId) {

        Client entity = clientRepository.getOne(clientId);

        entity.setFirstName(client.getFirstName());
        entity.setLastName(client.getLastName());
        entity.setDni(client.getDni());
        entity.setPhone(client.getPhone());

        save(entity);
    }

    public void editPassword(String password, Long clientId) {

        Client entity = clientRepository.getOne(clientId);

        entity.setPassword(password);

        save(entity);
    }

    */

}
