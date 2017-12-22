package com.gdantimi.demo.controller;

import com.gdantimi.demo.controller.error.ErrorDetail;
import com.gdantimi.demo.controller.error.RestError;
import com.gdantimi.demo.model.dto.UserDto;
import com.gdantimi.demo.model.entity.User;
import com.gdantimi.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

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
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> find(@PathVariable Long id) {
        UserDto user = userService.find(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(CONFLICT)
    @ResponseBody
    public RestError processDatabaseError(DataIntegrityViolationException e) {
        ErrorDetail uniqueNameError = ErrorDetail.builder()
                .field("name")
                .defaultMessage("The provided name is already in use. Please provide another name")
                .build();
        return RestError.builder()
                .errors(asList(uniqueNameError))
                .build();
    }
}
