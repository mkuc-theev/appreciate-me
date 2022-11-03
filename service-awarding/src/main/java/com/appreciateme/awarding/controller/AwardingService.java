package com.appreciateme.awarding.controller;

import com.appreciateme.awarding.exception.AwardingNotFoundException;
import com.appreciateme.awarding.exception.FailedToGetOpinionsAmountException;
import com.appreciateme.awarding.exception.FailedToGetRewardException;
import com.appreciateme.awarding.exception.NoSuchRewardInAwardingException;
import com.appreciateme.awarding.exception.UnableToClaimRewardException;
import com.appreciateme.awarding.exception.UnableToUseRewardException;
import com.appreciateme.awarding.model.Awarding;
import com.appreciateme.awarding.model.AwardingUtils;
import com.appreciateme.awarding.model.AwardingDTO;
import com.appreciateme.awarding.model.OwnedReward;
import com.appreciateme.reward.model.Reward;
import com.appreciateme.reward.model.RewardUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwardingService {

    private final AwardingRepository repository;

    /**
     * Get a list of all associations of users to their rewards
     * @return      list of all awardings as Awarding
     */
    public List<Awarding> getAll() {
        List<AwardingDTO> opinions = repository.findAll();

        return AwardingUtils.mapToAwardingList(opinions);
    }

    /**
     * Get one awarding by its identifier
     * @param id    awarding identifier
     * @return      particular awarding
     * @throws AwardingNotFoundException    when there is no awarding with specified ID
     */
    public Awarding getById(String id)
            throws AwardingNotFoundException {

        AwardingDTO awardingDTO = repository.findById(id)
                .orElseThrow(() -> new AwardingNotFoundException(id));

        return AwardingUtils.mapToAwarding(awardingDTO);
    }

    /**
     * Add provided reward to Awarding object for particular user, or create new if it doesn't exist.
     * This method make a request to OpinionMicroservice to get number of unused opinions for this user.
     * @param userId    identifier of user, who wants to get this reward
     * @param rewardId    identifier of reward, which should be added to particular user's Awarding object
     */
    public void claimReward(String userId, String rewardId) {
        Reward reward = obtainRewardFromRewardService(rewardId)
                .orElseThrow(() -> new FailedToGetRewardException(rewardId));

        validateClaimRequest(userId, reward);

        OwnedReward ownedReward = AwardingUtils.getOwnedReward(reward);

        AwardingDTO awardingDTO = getOrCreateAwarding(userId);
        awardingDTO.getRewards().add(ownedReward);

        repository.save(awardingDTO);

        notifyOpinionsToUseUsersOpinions(userId, reward.getRequiredOpinionAmount());
    }

    private void validateClaimRequest(String userId, Reward reward) {
        Integer userOpinionsAmount = obtainOpinionsAmountFromOpinionServiceForUser(userId)
                .orElseThrow(FailedToGetOpinionsAmountException::new);

        if (reward.getDateTo() != null && !AwardingUtils.isAvailable(reward.getDateFrom(), reward.getDateTo())) {
            throw new UnableToClaimRewardException(reward.getDateFrom(), reward.getDateTo());
        }

        if (userOpinionsAmount < reward.getRequiredOpinionAmount()) {
            throw new UnableToClaimRewardException(userOpinionsAmount, reward.getRequiredOpinionAmount());
        }
    }

    private AwardingDTO getOrCreateAwarding(String userId) {
        return repository.findById(userId).orElse(
                repository.save(
                        AwardingDTO.builder()
                                .userId(userId)
                                .rewards(new LinkedList<>())
                                .build()));
    }

    private Optional<Integer> obtainOpinionsAmountFromOpinionServiceForUser(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8002/opinions/unused/reviewedUser/amount/" + userId;
        return Optional.ofNullable(restTemplate.getForObject(url, Integer.class));
    }

    private Optional<Reward> obtainRewardFromRewardService(String rewardId) {
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8006/rewards/" + rewardId;
        return Optional.ofNullable(restTemplate.getForObject(url, Reward.class));
    }

    private void notifyOpinionsToUseUsersOpinions(String userId, int amount) {
        RestTemplate restTemplate = new RestTemplate();
        final String url = String.format("http://localhost:8002/opinions/use/%s?amount=%d", userId, amount);
        restTemplate.put(url, null);
    }

    /**
     * Mark particular reward owned by particular user as used
     * @param userId        user identifier
     * @param rewardId      reward identifier
     */
    public void useReward(String userId, String rewardId) {
        AwardingDTO awardingDTO = repository.findById(userId)
                .orElseThrow(() -> new AwardingNotFoundException(userId));

        OwnedReward ownedReward = awardingDTO.getRewards().stream()
                .filter(reward -> reward.getRewardId().equals(rewardId))
                .findAny()
                .orElseThrow(() -> new NoSuchRewardInAwardingException(userId, rewardId));

        if (!AwardingUtils.isAvailable(ownedReward.getDateFrom(), ownedReward.getDateTo())) {
            throw new UnableToUseRewardException(ownedReward.getDateFrom(), ownedReward.getDateTo());
        }

        ownedReward.setUsed(true);
        repository.save(awardingDTO);
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
     * Remove one awarding with specified id
     * @param id    identifier of specific awarding
     * @return      true if succeed
     */
    public boolean delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new AwardingNotFoundException(id));

        repository.deleteById(id);

        return true;
    }

}
