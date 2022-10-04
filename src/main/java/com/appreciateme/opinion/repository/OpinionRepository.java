package com.appreciateme.opinion.repository;

import com.appreciateme.opinion.model.Opinion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OpinionRepository extends MongoRepository<Opinion, String> {
}
