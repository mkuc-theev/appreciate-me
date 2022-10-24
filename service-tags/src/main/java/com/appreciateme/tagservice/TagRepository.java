package com.appreciateme.tagservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends MongoRepository<TagDTO, String> {

    Optional<TagDTO> findById(String s);

    List<TagDTO> findAll();
}
