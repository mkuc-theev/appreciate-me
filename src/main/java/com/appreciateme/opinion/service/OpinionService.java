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
    public List<OpinionDTO> getAllOpinions() {
        List<Opinion> opinions = repository.findAll();

        return OpinionMapper.toDtoList(opinions);
    }

    /**
     * Get one opinion by its identifier
     * @param id    identifier of specific opinion
     * @return      opinion with specified ID as OpinionDTO
     * @throws OpinionNotFoundException when there is no opinion with specified ID in database
     */
    public OpinionDTO getOpinionById(String id)
            throws OpinionNotFoundException {

        Opinion opinion = repository.findById(id)
                .orElseThrow(() -> new OpinionNotFoundException(id));

        return OpinionMapper.toDto(opinion);
    }

    /**
     * Get a list of all opinions made by particular user
     * @param id    identifier of specific user
     * @return      list of opinions as OpinionDTO
     */
    public List<OpinionDTO> getOpinionByOpinionUserId(String id) {
        List<Opinion> opinions = repository.findByOpinionUserId(id);

        return OpinionMapper.toDtoList(opinions);
    }

    /**
     * Get a list of all opinions of particular user
     * @param id    identifier of specific user
     * @return      list of opinions as OpinionDTO
     */
    public List<OpinionDTO> getOpinionByReviewedUserId(String id) {
        List<Opinion> opinions = repository.findByReviewedUserId(id);

        return OpinionMapper.toDtoList(opinions);

    }

    /**
     * Insert new opinion into database
     * @param opinionDTO    OpinionDTO object which should be inserted
     */
    public void addOpinion(OpinionDTO opinionDTO) {
        Opinion opinion = OpinionMapper.toOpinion(opinionDTO);

        repository.save(opinion);
        log.info("[OpinionService > createOpinion] opinion saved");
    }

    /**
     * Change many opinions according to opinionDTO, which has non-null
     * values in fields that should be changed
     * @param opinionDTO    OpinionDTO object with non-null fields which should be changed
     */
    public void updateMany(List<OpinionDTO> opinionDTOs, OpinionDTO opinionDTO) {
        System.out.println(opinionDTO.getDate());

        opinionDTOs.forEach(opinion -> {
            opinion.setOpinionUserID(opinionDTO.getOpinionUserID() != null
                    ? opinionDTO.getOpinionUserID() : opinion.getOpinionUserID());
            opinion.setReviewedUserID(opinionDTO.getReviewedUserID() != null
                    ? opinionDTO.getReviewedUserID() : opinion.getReviewedUserID());
            opinion.setPredefinedMessageID(opinionDTO.getPredefinedMessageID() != null
                    ? opinionDTO.getPredefinedMessageID() : opinion.getPredefinedMessageID());
            opinion.setOpinionMessage(opinionDTO.getOpinionMessage() != null
                    ? opinionDTO.getOpinionMessage() : opinion.getOpinionMessage());
            opinion.setDate(opinionDTO.getDate() != null
                    ? opinionDTO.getDate() : opinion.getDate());
        });

        List<Opinion> opinions = OpinionMapper.toList(opinionDTOs);
        for (Opinion opinion: opinions) {
            repository.save(opinion);
        }
    }

    /**
     * Edit existing opinion
     * @param opinionDTO    OpinionDTO which represent the latest state of existing object
     * @throws OpinionNotFoundException when there is no opinion with specified ID in database
     */
    public void updateOpinion(OpinionDTO opinionDTO)
            throws OpinionNotFoundException {

        repository.findById(opinionDTO.getId())
                .orElseThrow(() -> new OpinionNotFoundException(opinionDTO.getId()));

        Opinion opinion = OpinionMapper.toOpinion(opinionDTO);

        repository.save(opinion);
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
