package com.appreciateme.reward.controller;

import com.appreciateme.reward.model.RewardDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RewardRepository extends MongoRepository<RewardDTO, String> {

    /**
     * Get list of available (meaning on time) rewards that requires less or even than N
     * @param amount        number N of required opinions
     * @param currentDate   current date as timestamp
     * @return              list of rewards
     */
    @Query("{'requiredOpinionAmount' : { $lte : ?0 }, 'dateFrom': { $lte: ?1 }, 'dateTo': { $gte: ?1 } }")
    List<RewardDTO> getAllWithRequiredOpinionAmountLessOrEvenThan(int amount, long currentDate);
}
