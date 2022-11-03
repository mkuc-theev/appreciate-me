package com.appreciateme.credential.repository;

import com.appreciateme.credential.model.Credential;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CredentialRepository extends MongoRepository<Credential, String> {

  @Query("{'email': :#{#email}}")
  Optional<Credential> findByEmail(String email);


  boolean existsByEmail(String email);

  void deleteByEmail(String email);
}
