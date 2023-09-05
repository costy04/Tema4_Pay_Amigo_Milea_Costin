package com.spring.PayAmigo.controllers;

import com.spring.PayAmigo.entities.User;
import com.spring.PayAmigo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add_user")
    public ResponseEntity<User> addUser (@RequestBody User user){
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll (){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
}
