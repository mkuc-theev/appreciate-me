package com.appreciateme.usersservice.controller;

import com.appreciateme.usersservice.model.User;
import com.appreciateme.usersservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private UserService userService;

  @PostMapping(value = "/")
  public ResponseEntity<?> add(@RequestBody User user) {
    if (!User.isUserCorrect(user)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

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
    if (userService.existsById(id)) {
      return ResponseEntity.ok(userService.getById(id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @GetMapping(value = "/findByName")
  public ResponseEntity<List<User>> getByFirstNameAndLastName(@RequestParam String firstName,
      @RequestParam String lastName) {
    List<User> foundUsers = userService.getByFirstNameAndLastName(firstName, lastName);
    return foundUsers.size() == 0 ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        : ResponseEntity.ok(foundUsers);
  }

  @GetMapping(value = "/findByEmail")
  public ResponseEntity<User> getByEmail(@RequestParam String email) {
    return ResponseEntity.ok(userService.getByEmail(email));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<User> delete(@PathVariable String id) {
    userService.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
