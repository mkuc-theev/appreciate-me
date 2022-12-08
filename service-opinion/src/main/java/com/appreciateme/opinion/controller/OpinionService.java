package com.appreciateme.opinion.controller;

import com.appreciateme.opinion.model.OpinionCorrectnessStatus;
import com.appreciateme.opinion.model.OpinionDTO;
import com.appreciateme.opinion.model.OpinionUtils;
import com.appreciateme.opinion.exception.IncorrectOpinionException;
import com.appreciateme.opinion.exception.OpinionNotFoundException;
import com.appreciateme.opinion.model.Opinion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpinionService {

    private final String DELETION_REPLACEMENT = "DELETED_USER";
    private final OpinionRepository repository;


    /**
     * Get a list of all opinions
     * @return      list of all opinions as Opinion
     */
    public List<Opinion> getAll() {
        List<OpinionDTO> opinions = repository.findAll();

        return OpinionUtils.mapToOpinionList(opinions);
    }

    /**
     * Get one opinion by its identifier
     * @param id    identifier of specific opinion
     * @return      particular opinion
     * @throws OpinionNotFoundException when there is no opinion with specified ID
     */
    public Opinion getById(String id)
            throws OpinionNotFoundException {

        OpinionDTO opinionDTO = repository.findById(id)
                .orElseThrow(() -> new OpinionNotFoundException(id));

        return OpinionUtils.mapToOpinion(opinionDTO);
    }

    /**
     * Get a list of all opinions made for particular user
     * @param id    identifier of specific user
     * @return      list of opinions
     */
    public List<Opinion> getAllUnusedByReviewedUserId(String id) {
        List<OpinionDTO> opinionDTOs = repository.findAllUnusedByReviewedUserId(id);

        return OpinionUtils.mapToOpinionList(opinionDTOs);
    }

    /**
     * Insert new opinion into database
     * @param opinion    Opinion object which should be inserted
     * @return           id of created Opinion object
     */
    public String add(Opinion opinion) {
        OpinionCorrectnessStatus status = Opinion.isOpinionCorrect(opinion);

        if (!status.equals(OpinionCorrectnessStatus.CORRECT)) {
            throw new IncorrectOpinionException(status);
        }

        if (opinion.getDate() == null) {
            OpinionUtils.setCurrentDate(opinion);
        }

        opinion.setUsed(false);

        OpinionDTO opinionDTO = OpinionUtils.mapToDto(opinion);

        return repository.save(opinionDTO).getId();
    }

    /**
     * Edit existing opinion
     * @param opinion    Opinion object which represent the latest state of existing object
     * @return           updated opinion as Opinion
     * @throws OpinionNotFoundException when there is no opinion with specified ID in database
     */
    public Opinion update(Opinion opinion)
            throws OpinionNotFoundException {

        repository.findById(opinion.getId())
                .orElseThrow(() -> new OpinionNotFoundException(opinion.getId()));

        OpinionDTO opinionDTO = OpinionUtils.mapToDto(opinion);

        return OpinionUtils.mapToOpinion(repository.save(opinionDTO));
    }

    /**
     * Mark N unused opinions for user with specified Id as used
     * @param userId        id of user (reviewedUserId column)
     * @param amount        amount of N unused opinions that should be marked as used
     * @return              list of edited opinions
     */
    public List<Opinion> useOpinions(String userId, int amount) {
        List<OpinionDTO> opinions = repository.useOpinionsForUser(userId, amount);

        for (OpinionDTO opinionDTO: opinions) {
            repository.save(opinionDTO);
        }

        return OpinionUtils.mapToOpinionList(opinions);
    }

    /**
     * Handle user removal. Mark its id's in opinions made for / by this user with proper replacement.
     * @param userId    if of user to be removed
     * @return          list of opinions that were updated
     */
    public List<Opinion> replaceUserToDeleted(String userId) {
        List<OpinionDTO> opinions = repository.replaceUserToDeleted(userId, DELETION_REPLACEMENT);

        for (OpinionDTO opinionDTO: opinions) {
            repository.save(opinionDTO);
        }

        return OpinionUtils.mapToOpinionList(opinions);
    }

    /**
     * Remove whole collection of opinions from database
     * @return      true if succeed
     */
    public boolean clear() {
        repository.deleteAll();

        return true;
    }

    /**
     * Remove one opinion with specified id
     * @param id    identifier of specific opinion
     * @return      true if succeed
     */
    public boolean delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new OpinionNotFoundException(id));

        repository.deleteById(id);

        return true;
    }

}
