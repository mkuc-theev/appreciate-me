package com.appreciateme.usersservice.controller;

import com.appreciateme.usersservice.model.User;
import com.appreciateme.usersservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/")
    public ResponseEntity<?> add(@RequestBody User user) {
        userService.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(
                userService.getAll());
    }

    @GetMapping(value = "/findById")
    public ResponseEntity<User> getById(@RequestParam String id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping(value = "/findByName")
    public ResponseEntity<List<User>> getByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        System.out.println('x');
        return ResponseEntity.ok(userService.getByFirstNameAndLastName(firstName, lastName));
    }
}
