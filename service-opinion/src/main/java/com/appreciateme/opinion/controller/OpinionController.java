package com.appreciateme.opinion.controller;

import com.appreciateme.opinion.model.Opinion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/opinions")
@RequiredArgsConstructor
public class OpinionController {

    private final OpinionService service;

// GET
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

    @GetMapping("unused/reviewedUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> getAllUnusedByReviewedUserId(@PathVariable String userId) {
        return service.getAllUnusedByReviewedUserId(userId);
    }

    @GetMapping("unused/reviewedUser/amount/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Integer getAmountOfUnusedOpinionsByReviewedUserId(@PathVariable String userId) {
        return service.getAllUnusedByReviewedUserId(userId).size();
    }

// POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@RequestBody Opinion opinionRequest) {
        return service.add(opinionRequest);
    }

// PUT
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Opinion update(@RequestBody Opinion opinionRequest) {
        return service.update(opinionRequest);
    }

    @PutMapping("/use/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> useOpinionsToClaimReward(@PathVariable String userId, @RequestParam int amount) {
        return service.useOpinions(userId, amount);
    }

    @PutMapping("/remove/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Opinion> handleUserRemoval(@PathVariable String id) {
        return service.replaceUserToDeleted(id);
    }

// DELETE
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
