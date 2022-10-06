package com.appreciateme.usersservice.repository;

import com.appreciateme.usersservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {

}
