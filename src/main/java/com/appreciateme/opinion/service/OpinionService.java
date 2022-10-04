package com.appreciateme.opinion.service;

import com.appreciateme.opinion.dto.OpinionRequest;
import com.appreciateme.opinion.dto.OpinionResponse;
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

    private OpinionResponse mapToOpinionResponse(Opinion opinion) {
        return OpinionResponse.builder()
                .id(opinion.getId())
                .opinionUserID(opinion.getOpinionUserID())
                .reviewedUserID(opinion.getReviewedUserID())
                .predefinedMessageID(opinion.getPredefinedMessageID())
                .opinionMessage(opinion.getOpinionMessage())
                .build();
    }

    public List<OpinionResponse> getAllOpinions() {
        List<Opinion> opinions = repository.findAll();

        return opinions.stream()
                .map(this::mapToOpinionResponse)
                .toList();
    }

    public OpinionResponse getOpinionById(String id) {
        Opinion opinion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("[OpinionService > getOpinionById] Opinion with ID = %s not found", id)));

        return mapToOpinionResponse(opinion);
    }

    public void addOpinion(OpinionRequest opinionRequest) {
        Opinion opinion = Opinion.builder()
                .opinionUserID(opinionRequest.getOpinionUserID())
                .reviewedUserID(opinionRequest.getReviewedUserID())
                .predefinedMessageID(opinionRequest.getPredefinedMessageID())
                .opinionMessage(opinionRequest.getOpinionMessage())
                .build();

        repository.save(opinion);
        log.info("[OpinionService > createOpinion] opinion with ID = {} is saved correctly", opinion.getId());
    }



    public void updateOpinion(OpinionRequest opinionRequest) {

    }

    public void clear() {
        repository.deleteAll();
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
