package com.appreciateme.awarding.controller;

import com.appreciateme.awarding.model.Awarding;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/awardings")
@RequiredArgsConstructor
public class AwardingController {

    private final AwardingService service;

// GET
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Awarding> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Awarding getById(@PathVariable String id) {
        return service.getById(id);
    }

// POST
    @PostMapping("reward/claim")
    @ResponseStatus(HttpStatus.CREATED)
    public void claimReward(@RequestParam String userId, @RequestParam String rewardId) {
        service.claimReward(userId, rewardId);
    }

// PUT
    @PutMapping("reward/use")
    public void useReward(@RequestParam String userId, @RequestParam String rewardId) {
        service.useReward(userId, rewardId);
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
