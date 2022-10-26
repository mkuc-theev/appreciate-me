package com.appreciateme.reward.controller;

import com.appreciateme.reward.exception.FailedToGetRewardsException;
import com.appreciateme.reward.model.Reward;
import com.appreciateme.reward.model.RewardCorrectnessStatus;
import com.appreciateme.reward.model.RewardDTO;
import com.appreciateme.reward.model.RewardUtils;
import com.appreciateme.reward.exception.IncorrectRewardException;
import com.appreciateme.reward.exception.RewardNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardService {

    private final RewardRepository repository;

    public List<Reward> getAll() {
        List<RewardDTO> rewards = repository.findAll();

        return RewardUtils.mapToRewardList(rewards);
    }

    public Reward getById(String id)
            throws RewardNotFoundException {

        RewardDTO rewardDTO = repository.findById(id)
                .orElseThrow(() -> new RewardNotFoundException(id));

        return RewardUtils.mapToReward(rewardDTO);
    }

    public List<Reward> getAllForUser(String userId) {
        Integer opinionsAmount = getOpinionsAmountForUser(userId)
                .orElseThrow(FailedToGetRewardsException::new);

        List<RewardDTO> rewards = repository.getAllWithRequiredOpinionAmountLessOrEvenThan(opinionsAmount);
        return RewardUtils.mapToRewardList(rewards);
    }

    private Optional<Integer> getOpinionsAmountForUser(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8002/opinions/unused/reviewedUser/amount/" + userId;
        return Optional.ofNullable(restTemplate.getForObject(url, Integer.class));
    }

    public String add(Reward reward) {
        if (reward.getDateFrom() == null) {
            reward.setDateFrom(RewardUtils.setCurrentDate());
        }

        RewardCorrectnessStatus status = Reward.isCorrect(reward);

        if (!status.equals(RewardCorrectnessStatus.CORRECT)) {
            throw new IncorrectRewardException(status);
        }

        RewardDTO rewardDTO = RewardUtils.mapToDto(reward);

        return repository.save(rewardDTO).getId();
    }

    public Reward update(Reward reward)
            throws RewardNotFoundException {

        repository.findById(reward.getId())
                .orElseThrow(() -> new RewardNotFoundException(reward.getId()));

        RewardDTO rewardDTO = RewardUtils.mapToDto(reward);

        return RewardUtils.mapToReward(repository.save(rewardDTO));
    }

    public boolean clear() {
        repository.deleteAll();

        return true;
    }

    public boolean delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new RewardNotFoundException(id));

        repository.deleteById(id);

        return true;
    }

}
