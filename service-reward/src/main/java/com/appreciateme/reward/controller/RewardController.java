package com.appreciateme.reward.controller;

import com.appreciateme.reward.model.Reward;
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
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService service;

// GET
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Reward> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Reward getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Reward> getAllForUser(@PathVariable String userId) {
        return service.getAllForUser(userId);
    }


// POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@RequestBody Reward reward) {
        return service.add(reward);
    }


// PUT
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Reward update(@RequestBody Reward reward) {
        return service.update(reward);
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
