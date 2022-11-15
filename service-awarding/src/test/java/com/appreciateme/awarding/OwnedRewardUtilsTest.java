package com.appreciateme.awarding;

import com.appreciateme.awarding.model.OwnedReward;
import com.appreciateme.awarding.model.OwnedRewardDTO;
import com.appreciateme.awarding.model.OwnedRewardUtils;
import com.appreciateme.reward.model.Reward;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.appreciateme.awarding.TestObjects.OWNED_REWARD_1;
import static com.appreciateme.awarding.TestObjects.OWNED_REWARD_2;
import static com.appreciateme.awarding.TestObjects.OWNED_REWARD_3;
import static com.appreciateme.awarding.TestObjects.OWNED_REWARD_DTO_1;
import static com.appreciateme.awarding.TestObjects.OWNED_REWARD_DTO_2;
import static com.appreciateme.awarding.TestObjects.OWNED_REWARD_DTO_3;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OwnedRewardUtilsTest {

    final Reward REWARD = Reward.builder()
            .id("6351508c881167185d79e3d0")
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .requiredOpinionAmount(3)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom("2022-03-09 08:00:00")
            .dateTo("2023-06-09 08:00:00")
            .availabilityDays(30)
            .build();

    @Test
    @DisplayName("[ 1] given OwnedRewardDTO - when mapToOwnedReward() - then return OwnedReward")
    void givenOwnedRewardDTO_whenMapToOwnedReward_thenReturnOwnedReward() {
        OwnedReward ownedReward = OwnedRewardUtils.mapToOwnedReward(OWNED_REWARD_DTO_1);

        assertEquals(OWNED_REWARD_1, ownedReward);
    }

    @Test
    @DisplayName("[ 2] given OwnedReward - when mapToDto() - then return OwnedRewardDTO")
    void givenOwnedReward_whenMapToDto_thenReturnOwnedRewardDto() {
        OwnedRewardDTO ownedRewardDTO = OwnedRewardUtils.mapToDto(OWNED_REWARD_1);

        assertEquals(OWNED_REWARD_DTO_1, ownedRewardDTO);
    }

    @Test
    @DisplayName("[ 3] given List<OwnedRewardDTO> - when mapToOwnedRewardList() - then return List<OwnedReward>")
    void givenListOfOwnedRewardDTO_whenMapToOwnedRewardList_thenReturnListOfOwnedReward() {
        List<OwnedReward> ownedRewards = List.of(OWNED_REWARD_1, OWNED_REWARD_2, OWNED_REWARD_3);
        List<OwnedRewardDTO> ownedRewardDTOs = List.of(OWNED_REWARD_DTO_1, OWNED_REWARD_DTO_2, OWNED_REWARD_DTO_3);

        List<OwnedReward> result = OwnedRewardUtils.mapToOwnedRewardList(ownedRewardDTOs);

        assertEquals(ownedRewards, result);
    }

    @Test
    @DisplayName("[ 4] given List<OwnedReward> - when mapToDtoList() - then return List<OwnedRewardDTO>")
    void givenListOfOwnedReward_whenMapToDtoList_thenReturnListOfOwnedRewardDto() {
        List<OwnedReward> ownedRewards = List.of(OWNED_REWARD_1, OWNED_REWARD_2, OWNED_REWARD_3);
        List<OwnedRewardDTO> ownedRewardDTOs = List.of(OWNED_REWARD_DTO_1, OWNED_REWARD_DTO_2, OWNED_REWARD_DTO_3);

        List<OwnedRewardDTO> result = OwnedRewardUtils.mapToDtoList(ownedRewards);

        assertEquals(ownedRewardDTOs, result);
    }

    @Test
    @DisplayName("[ 5] given Reward - when mapToOwnedReward() - then return OwnedReward")
    void givenReward_whenMapToOwnedReward_thenReturnOwnedReward() {
        OwnedReward reward = OwnedRewardUtils.getOwnedReward(REWARD);
        reward.setDateFrom(OWNED_REWARD_1.getDateFrom());
        reward.setDateTo(OWNED_REWARD_1.getDateTo());

        assertEquals(OWNED_REWARD_1, reward);
    }
}
