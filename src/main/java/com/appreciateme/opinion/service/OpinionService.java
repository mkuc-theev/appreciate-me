package com.appreciateme.opinion.service;

import com.appreciateme.opinion.dto.OpinionDTO;
import com.appreciateme.opinion.dto.OpinionMapper;
import com.appreciateme.opinion.exception.IncorrectOpinionException;
import com.appreciateme.opinion.exception.OpinionNotFoundException;
import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.opinion.repository.OpinionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpinionService {

    private final static String DATE_REGEX = "^(19|20)[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";

    private final OpinionRepository repository;

    /**
     * Verify if provided String represents date in correct format
     * @param date      String with date to check
     * @return          true if date is in correct format, false if not
     */
    public static boolean isDateFormatCorrect(String date) {
        Pattern pattern = Pattern.compile(DATE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(date);
        return matcher.find();
    }

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
     * @return           saved Opinion
     */
    public Opinion add(Opinion opinion) {
        if (!isOpinionCorrect(opinion)) {
            throw new IncorrectOpinionException();
        }

        OpinionDTO opinionDTO = OpinionMapper.toDto(opinion);

        return OpinionMapper.toOpinion(repository.save(opinionDTO));
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

        OpinionDTO opinionDTO = OpinionMapper.toDto(opinion);

        return OpinionMapper.toOpinion(repository.save(opinionDTO));
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

    /**
     * Verify provided Opinion is not a null, has id, opinionUserId, ReviewedUserId and date
     * @param opinion   opinion object to check
     * @return          true if opinion is correct, false if not
     */
    public static boolean isOpinionCorrect(Opinion opinion) {
        if (opinion == null) {
            return false;
        }

        if (opinion.getId() == null || opinion.getId().isEmpty()) {
            return false;
        }

        if (opinion.getOpinionUserID() == null || opinion.getOpinionUserID().isEmpty()) {
            return false;
        }

        if (opinion.getReviewedUserID() == null || opinion.getReviewedUserID().isEmpty()) {
            return false;
        }

        return opinion.getDate() != null && !opinion.getDate().isEmpty() && isDateFormatCorrect(opinion.getDate());
    }


}
