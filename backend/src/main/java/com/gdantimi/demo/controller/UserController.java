package com.gdantimi.demo.controller;

import com.gdantimi.demo.model.dto.UserDto;
import com.gdantimi.demo.model.entity.User;
import com.gdantimi.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users/")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*", methods = RequestMethod.POST)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody @Valid UserDto user, HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        user = userService.save(user);
        response.addCookie(new Cookie("test", "2"));
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable Long id){
        User user = userService.find(id);
        if(user == null){
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(user, OK);
    }
}
