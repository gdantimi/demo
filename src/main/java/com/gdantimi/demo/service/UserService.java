package com.gdantimi.demo.service;

import com.gdantimi.demo.model.User;
import com.gdantimi.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public User find(Long id){
        return userRepository.findOne(id);
    }
}
