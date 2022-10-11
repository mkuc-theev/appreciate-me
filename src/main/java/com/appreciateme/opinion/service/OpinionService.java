package com.appreciateme.opinion.service;

import com.appreciateme.opinion.dto.OpinionDTO;
import com.appreciateme.opinion.dto.OpinionMapper;
import com.appreciateme.opinion.exception.OpinionNotFoundException;
import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.opinion.repository.OpinionRepository;
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

        return OpinionMapper.toOpinionList(opinions);
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

        return OpinionMapper.toOpinion(opinionDTO);
    }

    /**
     * Get a list of all opinions made by particular user
     * @param id    identifier of specific user
     * @return      list of opinions as OpinionDTO
     */
    public List<Opinion> getByOpinionUserId(String id) {
        List<OpinionDTO> opinionDTOs = repository.findByOpinionUserId(id);

        return OpinionMapper.toOpinionList(opinionDTOs);
    }

    /**
     * Get a list of all opinions of particular user
     * @param id    identifier of specific user
     * @return      list of opinions as OpinionDTO
     */
    public List<Opinion> getByReviewedUserId(String id) {
        List<OpinionDTO> opinionDTOs = repository.findByReviewedUserId(id);

        return OpinionMapper.toOpinionList(opinionDTOs);
    }

    /**
     * Get a list of all opinions made at specified date
     * @param date      date when the opinions were made
     * @return          list of opinions as OpinionDTO
     */
    public List<Opinion> getByDate(String date) {
        List<OpinionDTO> opinionDTOs = repository.findByDate(date);

        return OpinionMapper.toOpinionList(opinionDTOs);
    }

    /**
     * Insert new opinion into database
     * @param opinion    OpinionDTO object which should be inserted
     */
    public void add(Opinion opinion) {
        OpinionDTO opinionDTO = OpinionMapper.toDto(opinion);

        repository.save(opinionDTO);
        log.info("[OpinionService > createOpinion] opinion saved");
    }

    /**
     * Edit existing opinion
     * @param opinion    OpinionDTO which represent the latest state of existing object
     * @throws OpinionNotFoundException when there is no opinion with specified ID in database
     */
    public void update(Opinion opinion)
            throws OpinionNotFoundException {

        repository.findById(opinion.getId())
                .orElseThrow(() -> new OpinionNotFoundException(opinion.getId()));

        OpinionDTO opinionDTO = OpinionMapper.toDto(opinion);

        repository.save(opinionDTO);
        log.info("[OpinionService > updateOpinion] updated opinion with ID = {}", opinionDTO.getId());
    }

    /**
     * Remove whole collection of opinions from database
     */
    public void clear() {
        repository.deleteAll();
        log.info("[OpinionService > clear] all opinions have been removed");
    }

    /**
     * Remove one opinion with specified id
     * @param id        identifier of specific opinion
     */
    public void delete(String id) {
        repository.deleteById(id);
        log.info("[OpinionService > delete] removed opinion with ID = {}", id);
    }
}
