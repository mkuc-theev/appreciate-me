package com.appreciateme.opinion.controller;

import com.appreciateme.opinion.model.OpinionDTO;
import com.appreciateme.opinion.model.OpinionMapper;
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

        return OpinionMapper.toOpinionList(opinions);
    }

    public Opinion getById(String id)
            throws OpinionNotFoundException {

        OpinionDTO opinionDTO = repository.findById(id)
                .orElseThrow(() -> new OpinionNotFoundException(id));

        return OpinionMapper.toOpinion(opinionDTO);
    }

    public List<Opinion> getByOpinionUserId(String id) {
        List<OpinionDTO> opinionDTOs = repository.findByOpinionUserId(id);

        return OpinionMapper.toOpinionList(opinionDTOs);
    }

    public List<Opinion> getByReviewedUserId(String id) {
        List<OpinionDTO> opinionDTOs = repository.findByReviewedUserId(id);

        return OpinionMapper.toOpinionList(opinionDTOs);
    }

    public List<Opinion> getByDate(String date) {
        long timestamp = OpinionMapper.mapStringDateToLong(date);
        List<OpinionDTO> opinionDTOs = repository.findByDate(timestamp);

        return OpinionMapper.toOpinionList(opinionDTOs);
    }

    public Opinion add(Opinion opinion) {
        if (!Opinion.isOpinionCorrect(opinion)) {
            throw new IncorrectOpinionException();
        }

        OpinionDTO opinionDTO = OpinionMapper.toDto(opinion);

        return OpinionMapper.toOpinion(repository.save(opinionDTO));
    }

    public Opinion update(Opinion opinion)
            throws OpinionNotFoundException {

        repository.findById(opinion.getId())
                .orElseThrow(() -> new OpinionNotFoundException(opinion.getId()));

        OpinionDTO opinionDTO = OpinionMapper.toDto(opinion);

        return OpinionMapper.toOpinion(repository.save(opinionDTO));
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
