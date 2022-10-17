package com.appreciateme.usersservice.service;

import com.appreciateme.usersservice.model.User;
import com.appreciateme.usersservice.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public void add(User user) {
    repository.insert(user);
  }

  public List<User> getAll() {
    return repository.findAll();
  }

  public User getById(String id) {
    return repository.findById(id).orElseThrow(
        () -> new RuntimeException(String.format("Error 404! User with id: %s not found!", id)));
  }

  public List<User> getByFirstNameAndLastName(String firstName, String lastName) {
    return repository.findByFirstNameAndLastName(firstName, lastName);
  }

  public User getByEmail(String email) {
    return repository.findByEmail(email);
  }

  public boolean existsById(String id) {
    return repository.existsById(id);
  }

  public boolean existsByEmail(String email) { return repository.existsByEmail(email);}

  public void deleteById(String id) {
    repository.deleteById(id);
  }
}
