package com.appreciateme.opinion.controller;

import com.appreciateme.opinion.model.OpinionDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OpinionRepository extends MongoRepository<OpinionDTO, String> {

    /**
     * Get a list of opinions made by user with specified ID
     * @param id    identifier of User
     * @return      list of Opinions made by this user
     */
    @Query("{'opinionUserID': ?0}")
    List<OpinionDTO> findByOpinionUserId(String id);

    /**
     * Get a list of opinions for user with specified ID
     * @param id    identifier of User
     * @return      list of Opinions for this user
     */
    @Query("{'reviewedUserID': ?0}")
    List<OpinionDTO> findByReviewedUserId(String id);

    /**
     * Get a list of opinions made in specified date
     * @param date  day as long (epoch date format)
     * @return      list of opinions made at this particular date
     */
    @Query("{'date': ?0}")
    List<OpinionDTO> findByDate(long date);
}
