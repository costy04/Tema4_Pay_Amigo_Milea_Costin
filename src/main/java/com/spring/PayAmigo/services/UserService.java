package com.spring.PayAmigo.services;

import com.spring.PayAmigo.entities.User;
import com.spring.PayAmigo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll () {
        return userRepository.findAll();
    }
}
