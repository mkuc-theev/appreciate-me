package com.appreciateme.usersservice.repository;

import com.appreciateme.usersservice.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends MongoRepository<User, String> {

  @Query("{'firstName': :#{#first}, 'lastName': :#{#last}}")
  List<User> findByFirstNameAndLastName(@Param("first") String first, @Param("last") String last);

  @Query("{'email': :#{#email}}")
  Optional<User> findByEmail(@Param("email") String email);

  boolean existsByEmail(String email);

}
