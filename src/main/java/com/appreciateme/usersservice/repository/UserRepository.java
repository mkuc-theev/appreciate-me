package com.appreciateme.usersservice.repository;

import com.appreciateme.usersservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{'firstName': :#{#first}, 'lastName': :#{#last}}")
    List<User> findByFirstNameAndLastName(@Param("first") String first,
                                         @Param("last") String last);

    @Query("{'email': :#{#email}}")
    List<User> findByEmail(@Param("email") String email);
}
