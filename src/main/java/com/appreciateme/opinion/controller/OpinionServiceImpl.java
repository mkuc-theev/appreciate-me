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
public class OpinionServiceImpl implements OpinionService {

    private final OpinionRepository repository;

    public List<Opinion> getAll() {
        List<OpinionDTO> opinions = repository.findAll();

        return OpinionUtils.mapToOpinionList(opinions);
    }

    public Opinion getById(String id)
            throws OpinionNotFoundException {

        OpinionDTO opinionDTO = repository.findById(id)
                .orElseThrow(() -> new OpinionNotFoundException(id));

        return OpinionUtils.mapToOpinion(opinionDTO);
    }

    public List<Opinion> getByOpinionUserId(String id) {
        List<OpinionDTO> opinionDTOs = repository.findByOpinionUserId(id);

        return OpinionUtils.mapToOpinionList(opinionDTOs);
    }

    public List<Opinion> getByReviewedUserId(String id) {
        List<OpinionDTO> opinionDTOs = repository.findByReviewedUserId(id);

        return OpinionUtils.mapToOpinionList(opinionDTOs);
    }

    public List<Opinion> getByDate(String date) {
        long timestamp = OpinionUtils.mapStringDateToLong(date);
        List<OpinionDTO> opinionDTOs = repository.findByDate(timestamp);

        return OpinionUtils.mapToOpinionList(opinionDTOs);
    }

    public String add(Opinion opinion) {
        if (!Opinion.isOpinionCorrect(opinion)) {
            throw new IncorrectOpinionException();
        }

        if (opinion.getDate() == null) {
            OpinionUtils.setCurrentDate(opinion);
        }

        OpinionDTO opinionDTO = OpinionUtils.mapToDto(opinion);

        return repository.save(opinionDTO).getId();
    }

    public Opinion update(Opinion opinion)
            throws OpinionNotFoundException {

        repository.findById(opinion.getId())
                .orElseThrow(() -> new OpinionNotFoundException(opinion.getId()));

        OpinionDTO opinionDTO = OpinionUtils.mapToDto(opinion);

        return OpinionUtils.mapToOpinion(repository.save(opinionDTO));
    }

    public boolean clear() {
        repository.deleteAll();

        return true;
    }

    public boolean delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new OpinionNotFoundException(id));

        repository.deleteById(id);

        return true;
    }

}
