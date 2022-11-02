package com.appreciateme.opinion.controller;

import com.appreciateme.opinion.model.OpinionDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OpinionRepository extends MongoRepository<OpinionDTO, String> {

    /**
     * Get a list of opinions made by user with specified ID
     * @param id    identifier of User
     * @return      list of Opinions made by this user
     */
    @Query("{'opinionUserId': ?0}")
    List<OpinionDTO> findAllUnusedByOpinionUserId(String id);

    /**
     * Get a list of opinions for user with specified ID
     * @param id    identifier of User
     * @return      list of Opinions for this user
     */
    @Query("{'reviewedUserId': ?0, 'used': false}")
    List<OpinionDTO> findAllUnusedByReviewedUserId(String id);

    /**
     * Get a list of opinions made in specified date
     * @param date  day as long (epoch date format)
     * @return      list of opinions made at this particular date
     */
    @Query("{'date': ?0}")
    List<OpinionDTO> findAllByDate(long date);

    /**
     * Get list of N unused opinions for specified user, then mark them as used
     * @param userId            user identifier
     * @param opinionsAmount    number N of opinions that should be marked as used
     * @return                  list of N marked as used opinions
     */
    @Aggregation(pipeline = {
            "{ $match: { 'reviewedUserId': ?0, 'used': false } }",
            "{ $limit: ?1 }",
            "{ $set: { 'used': true } }"})
    List<OpinionDTO> useOpinionsForUser(String userId, int opinionsAmount);
}
