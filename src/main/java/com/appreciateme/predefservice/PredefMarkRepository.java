package com.appreciateme.predefservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PredefMarkRepository extends MongoRepository<PredefMark, String> {


    Optional<PredefMark> findById(String s);

    List<PredefMark> findAll();

    Optional<PredefMark> findByName(String name);
}
