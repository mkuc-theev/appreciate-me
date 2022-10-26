package com.appreciateme.awarding.controller;

import com.appreciateme.awarding.exception.AwardingNotFoundException;
import com.appreciateme.awarding.exception.FailedToGetOpinionsAmountException;
import com.appreciateme.awarding.exception.NoSuchRewardInAwardingException;
import com.appreciateme.awarding.exception.TooFewOpinionsException;
import com.appreciateme.awarding.model.Awarding;
import com.appreciateme.awarding.model.AwardingUtils;
import com.appreciateme.awarding.model.AwardingDTO;
import com.appreciateme.awarding.model.OwnedReward;
import com.appreciateme.reward.model.Reward;
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

    public List<Awarding> getAll() {
        List<AwardingDTO> opinions = repository.findAll();

        return AwardingUtils.mapToAwardingList(opinions);
    }

    public Awarding getById(String id)
            throws AwardingNotFoundException {

        AwardingDTO awardingDTO = repository.findById(id)
                .orElseThrow(() -> new AwardingNotFoundException(id));

        return AwardingUtils.mapToAwarding(awardingDTO);
    }

    public void claimReward(String userId, Reward reward) {
        Integer userOpinionsAmount = obtainOpinionsAmountFromOpinionServiceForUser(userId)
                .orElseThrow(FailedToGetOpinionsAmountException::new);

        if (userOpinionsAmount < reward.getRequiredOpinionAmount()) {
            throw new TooFewOpinionsException(userOpinionsAmount, reward.getRequiredOpinionAmount());
        }

        Optional<AwardingDTO> awardingDTOOptional = repository.findById(userId);
        AwardingDTO awardingDTO = awardingDTOOptional.orElse(
                repository.save(
                        AwardingDTO.builder()
                                .userId(userId)
                                .rewards(new LinkedList<>())
                                .build()));

        OwnedReward ownedReward = AwardingUtils.mapToOwnedReward(reward);
        ownedReward.setDateFrom(AwardingUtils.getCurrentDate());
        ownedReward.setDateTo(AwardingUtils.getFutureDate(reward.getAvailabilityDays()));

        awardingDTO.getRewards().add(ownedReward);
        repository.save(awardingDTO);

        notifyOpinionsToUseUsersOpinions(userId, reward.getRequiredOpinionAmount());
    }

    private Optional<Integer> obtainOpinionsAmountFromOpinionServiceForUser(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8002/opinions/unused/reviewedUser/amount/" + userId;
        return Optional.ofNullable(restTemplate.getForObject(url, Integer.class));
    }

    private void notifyOpinionsToUseUsersOpinions(String userId, int amount) {
        RestTemplate restTemplate = new RestTemplate();
        final String url = String.format("http://localhost:8002/opinions/use/%s?amount=%d", userId, amount);
        restTemplate.put(url, null);
    }

    public void useReward(String userId, String rewardId) {
        AwardingDTO awardingDTO = repository.findById(userId)
                .orElseThrow(() -> new AwardingNotFoundException(userId));

        OwnedReward ownedReward = awardingDTO.getRewards().stream()
                .filter(reward -> reward.getRewardId().equals(rewardId))
                .findAny()
                .orElseThrow(() -> new NoSuchRewardInAwardingException(userId, rewardId));

        ownedReward.setUsed(true);
        repository.save(awardingDTO);
    }

    public boolean clear() {
        repository.deleteAll();

        return true;
    }

    public boolean delete(String id) {
        repository.findById(id)
                .orElseThrow(() -> new AwardingNotFoundException(id));

        repository.deleteById(id);

        return true;
    }

}
