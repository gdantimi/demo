package com.gdantimi.demo.service;

import com.gdantimi.demo.model.dto.UserDto;
import com.gdantimi.demo.model.entity.User;
import com.gdantimi.demo.repository.UserRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private UserRepository userRepository;

    public UserDto save(UserDto user){
        User userEntity = mapper.map(user, User.class);
        userRepository.save(userEntity);
        user = mapper.map(userEntity, UserDto.class);
        return user;
    }

    public User find(Long id){
        return userRepository.findOne(id);
    }
}
