package com.appreciateme.opinion.controller;

import com.appreciateme.opinion.exception.OpinionNotFoundException;
import com.appreciateme.opinion.model.Opinion;

import java.util.List;

public interface OpinionService {

    /**
     * Get a list of all opinions from the database
     * @return      List of all opinions as OpinionDTO
     */
    List<Opinion> getAll();

    /**
     * Get one opinion by its identifier
     * @param id    identifier of specific opinion
     * @return      opinion with specified ID as OpinionDTO
     * @throws OpinionNotFoundException when there is no opinion with specified ID in database
     */
    Opinion getById(String id)
            throws OpinionNotFoundException;

    /**
     * Get a list of all opinions made by particular user
     * @param id    identifier of specific user
     * @return      list of opinions as OpinionDTO
     */
    List<Opinion> getByOpinionUserId(String id);

    /**
     * Get a list of all opinions of particular user
     * @param id    identifier of specific user
     * @return      list of opinions as OpinionDTO
     */
    List<Opinion> getByReviewedUserId(String id);

    /**
     * Get a list of all opinions made at specified date
     * @param date      date when the opinions were made
     * @return          list of opinions as OpinionDTO
     */
    List<Opinion> getByDate(String date);

    /**
     * Insert new opinion into database
     * @param opinion    OpinionDTO object which should be inserted
     * @return           saved Opinion
     */
    String add(Opinion opinion);

    /**
     * Edit existing opinion
     * @param opinion    OpinionDTO which represent the latest state of existing object
     * @throws OpinionNotFoundException when there is no opinion with specified ID in database
     * @return           updated Opinion
     */
    Opinion update(Opinion opinion)
            throws OpinionNotFoundException;
    /**
     * Remove whole collection of opinions from database
     * @return      true if succeed
     */
    boolean clear();

    /**
     * Remove one opinion with specified id
     * @param id    identifier of specific opinion
     * @return      true if succeed
     */
    boolean delete(String id);
}
