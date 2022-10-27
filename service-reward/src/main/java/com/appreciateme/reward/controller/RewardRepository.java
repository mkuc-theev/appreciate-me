package com.appreciateme.reward.controller;

import com.appreciateme.reward.model.RewardDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RewardRepository extends MongoRepository<RewardDTO, String> {

    /**
     * Get list of rewards that requires less or even than N
     * @param amount    number N of required opinions
     * @return          list of rewards
     */
    @Query("{'requiredOpinionAmount' : { $lte : ?0}}")
    List<RewardDTO> getAllWithRequiredOpinionAmountLessOrEvenThan(int amount);
}
