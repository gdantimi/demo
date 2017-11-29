package com.gdantimi.demo.controller;

import com.gdantimi.demo.model.User;
import com.gdantimi.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user){
        user = userService.save(user);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable Long id){
        User user = userService.findUser(id);
        if(user == null){
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<User>(user, OK);
    }
}
