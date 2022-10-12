package com.appreciateme.opinion.controller;

import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.opinion.service.OpinionService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public List<Opinion> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Opinion getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("opinionUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getByOpinionUserId(@PathVariable String id) {
        return service.getByOpinionUserId(id);
    }

    @GetMapping("reviewedUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getByReviewedUserId(@PathVariable String id) {
        return service.getByReviewedUserId(id);
    }

    @GetMapping("date/{date}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getByDate(@PathVariable String date) {
        return service.getByDate(date);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Opinion add(@RequestBody Opinion opinionRequest)
            throws JsonProcessingException {

        return service.add(opinionRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Opinion update(@RequestBody Opinion opinionRequest) {
        return service.update(opinionRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean clear() {
        return service.clear();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable String id) {
        return service.delete(id);
    }

}
