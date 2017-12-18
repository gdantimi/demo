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

    public UserDto save(UserDto userDto){
        User user = mapper.map(userDto, User.class);
        userRepository.save(user);
        userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    public UserDto find(Long id){
        User user = userRepository.findOne(id);
        return mapper.map(user, UserDto.class);
    }

    public User update(UserDto userDto) {
        User user = userRepository.findOne(userDto.getId());
        if(user == null){
            return null;
        }
        mapper.map(userDto, user);
        return userRepository.save(user);

    }
}
