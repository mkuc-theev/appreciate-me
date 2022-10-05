package com.appreciateme.opinion.repository;

import com.appreciateme.opinion.model.Opinion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OpinionRepository extends MongoRepository<Opinion, String> {

    /**
     * Get a List of opinions made by user with specified ID
     * @param id    identifier of User
     * @return      List of Opinions made by this user
     */
    @Query("{'opinionUser': ?0}")
    List<Opinion> findByOpinionUserId(String id);

    /**
     * Get a List of opinions for user with specified ID
     * @param id    identifier of User
     * @return      List of Opinions for this user
     */
    @Query("{'reviewedUser': ?0}")
    List<Opinion> findByReviewedUserId(String id);
}
