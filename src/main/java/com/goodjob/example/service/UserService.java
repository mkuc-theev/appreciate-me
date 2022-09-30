package com.goodjob.example.service;

import com.goodjob.example.model.User;
import com.goodjob.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void add(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Error 404: User with ID = %s not found", user.getId())));

        repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Error 404: User with ID = %s not found", id)));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
