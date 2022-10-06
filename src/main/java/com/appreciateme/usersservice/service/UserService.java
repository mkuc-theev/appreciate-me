package com.appreciateme.usersservice.service;

import com.appreciateme.usersservice.model.User;
import com.appreciateme.usersservice.repository.UserRepository;
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

    public List<User> getAll() {
        return repository.findAll();
    }
}
