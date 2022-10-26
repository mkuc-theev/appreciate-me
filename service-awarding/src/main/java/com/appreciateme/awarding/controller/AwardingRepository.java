package com.appreciateme.awarding.controller;

import com.appreciateme.awarding.model.AwardingDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AwardingRepository extends MongoRepository<AwardingDTO, String> {
}
