package com.appreciateme.example.repository;

import com.appreciateme.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
