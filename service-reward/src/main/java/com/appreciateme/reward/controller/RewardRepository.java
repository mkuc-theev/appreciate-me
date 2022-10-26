package com.appreciateme.reward.controller;

import com.appreciateme.reward.model.RewardDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RewardRepository extends MongoRepository<RewardDTO, String> {

    @Query("{'requiredOpinionAmount' : { $lte : ?0}}")
    List<RewardDTO> getAllWithRequiredOpinionAmountLessOrEvenThan(int amount);
}
