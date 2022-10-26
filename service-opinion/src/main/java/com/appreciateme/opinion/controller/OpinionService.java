package com.appreciateme.opinion.controller;

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

    private final OpinionRepository repository;

    /**
     * Get a list of all opinions from the database
     * @return      List of all opinions as OpinionDTO
     */
    public List<Opinion> getAll() {
        List<OpinionDTO> opinions = repository.findAll();

        return OpinionUtils.mapToOpinionList(opinions);
    }

    /**
     * Get one opinion by its identifier
     * @param id    identifier of specific opinion
     * @return      opinion with specified ID as OpinionDTO
     * @throws OpinionNotFoundException when there is no opinion with specified ID in database
     */
    public Opinion getById(String id)
            throws OpinionNotFoundException {

        OpinionDTO opinionDTO = repository.findById(id)
                .orElseThrow(() -> new OpinionNotFoundException(id));

        return OpinionUtils.mapToOpinion(opinionDTO);
    }

    /**
     * Get a list of all opinions of particular user
     * @param id    identifier of specific user
     * @return      list of opinions as OpinionDTO
     */
    public List<Opinion> getAllUnusedByReviewedUserId(String id) {
        List<OpinionDTO> opinionDTOs = repository.findAllUnusedByReviewedUserId(id);

        return OpinionUtils.mapToOpinionList(opinionDTOs);
    }

    /**
     * Insert new opinion into database
     * @param opinion    OpinionDTO object which should be inserted
     * @return           saved Opinion
     */
    public String add(Opinion opinion) {
        if (!Opinion.isOpinionCorrect(opinion)) {
            throw new IncorrectOpinionException();
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
     * @param opinion    OpinionDTO which represent the latest state of existing object
     * @throws OpinionNotFoundException when there is no opinion with specified ID in database
     * @return           updated Opinion
     */
    public Opinion update(Opinion opinion)
            throws OpinionNotFoundException {

        repository.findById(opinion.getId())
                .orElseThrow(() -> new OpinionNotFoundException(opinion.getId()));

        OpinionDTO opinionDTO = OpinionUtils.mapToDto(opinion);

        return OpinionUtils.mapToOpinion(repository.save(opinionDTO));
    }

    public List<Opinion> useOpinions(String userId, int amount) {
        List<OpinionDTO> opinions = repository.useOpinionsForUser(userId, amount);

        for(OpinionDTO opinionDTO: opinions) {
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
