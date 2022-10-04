package com.appreciateme.opinion.controller;

import com.appreciateme.opinion.dto.OpinionDTO;
import com.appreciateme.opinion.service.OpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/opinions")
@RequiredArgsConstructor
public class OpinionController {

    private final OpinionService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OpinionDTO> getAll() {
        return service.getAllOpinions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OpinionDTO getById(@PathVariable String id) {
        return service.getOpinionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody OpinionDTO opinionRequest) {
        service.addOpinion(opinionRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody OpinionDTO opinionRequest) {
        service.updateOpinion(opinionRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        service.clear();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

}
