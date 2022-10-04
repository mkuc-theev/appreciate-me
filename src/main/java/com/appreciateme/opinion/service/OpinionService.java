package com.appreciateme.opinion.service;

import com.appreciateme.opinion.dto.OpinionDTO;
import com.appreciateme.opinion.dto.OpinionMapper;
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

    public List<OpinionDTO> getAllOpinions() {
        List<Opinion> opinions = repository.findAll();

        return opinions.stream()
                .map(OpinionMapper::toDto)
                .toList();
    }

    public OpinionDTO getOpinionById(String id) {
        Opinion opinion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("[OpinionService > getOpinionById] Opinion with ID = %s not found", id)));

        return OpinionMapper.toDto(opinion);
    }

    public void addOpinion(OpinionDTO opinionDTO) {
        Opinion opinion = OpinionMapper.toOpinion(opinionDTO);

        repository.save(opinion);
        log.info("[OpinionService > createOpinion] opinion with ID = {} is saved correctly", opinion.getId());
    }



    public void updateOpinion(OpinionDTO opinionDTO) {
        repository.findById(opinionDTO.getId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("[]")));

        Opinion opinion = OpinionMapper.toOpinion(opinionDTO);

        repository.save(opinion);
    }

    public void clear() {
        repository.deleteAll();
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
