package com.gdantimi.demo.mapper;

import com.gdantimi.demo.model.dto.UserDto;
import com.gdantimi.demo.model.entity.Sector;
import com.gdantimi.demo.model.entity.User;
import com.gdantimi.demo.service.SectorService;
import org.dozer.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UserConverter extends DozerConverter<UserDto, User> {

    @Autowired
    private SectorService sectorService;

    public UserConverter() {
        super(UserDto.class, User.class);
    }

    @Override
    public User convertTo(UserDto userDto, User user) {
        if(userDto == null){
            return null;
        }
        if(user == null){
            user = User.builder().build();
        }
        user.setName(userDto.getName());
        user.setSectors(sectorService.findAllById(userDto.getSectorsIds()));
        user.setTermsAgreed(userDto.isTermsAgreed());

        return user;
    }

    @Override
    public UserDto convertFrom(User user, UserDto userDto) {
        if(user == null){
            return null;
        }
        if(userDto == null){
            userDto = UserDto.builder().build();
        }
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        List<Long> sectorIds = user.getSectors().stream()
                .map(Sector::getId)
                .collect(toList());
        userDto.setSectorsIds(sectorIds);
        userDto.setTermsAgreed(user.isTermsAgreed());

        return userDto;
    }
}