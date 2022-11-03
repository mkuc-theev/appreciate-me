package com.appreciateme.usersservice.controller;

import com.appreciateme.credential.model.Credential;
import com.appreciateme.credential.model.Role;
import com.appreciateme.usersservice.model.User;
import com.appreciateme.usersservice.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
public class UserController {

  @Value("${services.credentials.host}")
  private String credentialsHost;
  @Value("${services.credentials.port}")
  private String credentialsPort;
  @Autowired
  private UserService userService;
  @Autowired
  private RestTemplate restTemplate;

  @PostMapping(value = "/")
  public ResponseEntity<?> add(@RequestBody User user) {
    if (!User.isUserCorrect(user) || userService.existsByEmail(user.getEmail())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    String endpoint = "http://%s:%s/credentials/".formatted(credentialsHost,
        credentialsPort);
    System.out.println(endpoint);

    ResponseEntity<?> e = restTemplate.postForEntity(endpoint,
        new Credential(user.getEmail(), Credential.generateDefaultLengthRandomPassword(),
            Role.USER),
        ResponseEntity.class);

    if (e.getStatusCode() != HttpStatus.CREATED) {
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
    if (userService.existsByEmail(email)) {
      return ResponseEntity.ok(userService.getByEmail(email));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<User> delete(@PathVariable String id) {
    userService.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
