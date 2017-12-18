package com.gdantimi.demo.controller;

import com.gdantimi.demo.model.dto.UserDto;
import com.gdantimi.demo.model.entity.User;
import com.gdantimi.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody @Valid UserDto userDto) {
        userDto = userService.save(userDto);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserDto userDto) {
        User user = userService.update(userDto);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> find(@PathVariable Long id){
        UserDto user = userService.find(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
