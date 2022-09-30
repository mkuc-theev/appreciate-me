package com.goodjob.example.repository;

import com.goodjob.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
