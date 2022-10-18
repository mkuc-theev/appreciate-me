package com.appreciateme.predefservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PredefMarkRepository extends MongoRepository<PredefMarkDTO, String> {


    Optional<PredefMarkDTO> findById(String s);

    List<PredefMarkDTO> findAll();

    Optional<PredefMarkDTO> findByName(String name);
}
