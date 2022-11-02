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

    /**
     * Get a list of all rewards
     * @return      list of all rewards as Reward
     */
    public List<Reward> getAll() {
        List<RewardDTO> rewards = repository.findAll();

        return RewardUtils.mapToRewardList(rewards);
    }

    /**
     * Get one reward by its identifier
     * @param id    identifier of specific reward
     * @return      particular reward as Reward
     * @throws RewardNotFoundException
     */
    public Reward getById(String id)
            throws RewardNotFoundException {

        RewardDTO rewardDTO = repository.findById(id)
                .orElseThrow(() -> new RewardNotFoundException(id));

        return RewardUtils.mapToReward(rewardDTO);
    }

    /**
     * List all rewards that can be claimed by user with provided id.
     * This method send request to Opinion Microservice to get number of unused opinions.
     * @param userId    identifier of user
     * @return          list of rewards for particular user
     */
    public List<Reward> getAllForUser(String userId) {
        Integer opinionsAmount = getOpinionsAmountForUser(userId)
                .orElseThrow(FailedToGetRewardsException::new);

        long currentDate = RewardUtils.getCurrentDateLong();

        List<RewardDTO> rewards = repository.getAllWithRequiredOpinionAmountLessOrEvenThan(opinionsAmount, currentDate);
        return RewardUtils.mapToRewardList(rewards);
    }

    private Optional<Integer> getOpinionsAmountForUser(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8002/opinions/unused/reviewedUser/amount/" + userId;
        return Optional.ofNullable(restTemplate.getForObject(url, Integer.class));
    }

    /**
     * Insert new reward into database
     * @param reward    Reward object which should be inserted
     * @return          id of created Reward object
     */
    public String add(Reward reward) {
        if (reward.getDateFrom() == null) {
            reward.setDateFrom(RewardUtils.getCurrentDate());
        }

        RewardCorrectnessStatus status = Reward.isCorrect(reward);

        if (!status.equals(RewardCorrectnessStatus.CORRECT)) {
            throw new IncorrectRewardException(status);
        }

        RewardDTO rewardDTO = RewardUtils.mapToDto(reward);

        return repository.save(rewardDTO).getId();
    }

    /**
     * Edit existing reward
     * @param reward    Reward object which represents the latest state of existing object
     * @return          updated reward as Reward
     * @throws RewardNotFoundException  when there is no reward with specified ID in database
     */
    public Reward update(Reward reward)
            throws RewardNotFoundException {

        repository.findById(reward.getId())
                .orElseThrow(() -> new RewardNotFoundException(reward.getId()));

        RewardDTO rewardDTO = RewardUtils.mapToDto(reward);

        return RewardUtils.mapToReward(repository.save(rewardDTO));
    }

    /**
     * Remove whole collection of opinions from database
     * @return      true if succeed
     */
    public boolean clear() {
        repository.deleteAll();

        return true;
    }

    /**
     * Remove one reward with specified id
     * @param id    identifier of specific reward
     * @return      true if succeed
     */
    public boolean delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new RewardNotFoundException(id));

        repository.deleteById(id);

        return true;
    }

}
