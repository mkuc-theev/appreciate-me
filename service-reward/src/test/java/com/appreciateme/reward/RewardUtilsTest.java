package com.appreciateme.reward;

import com.appreciateme.reward.model.Reward;
import com.appreciateme.reward.model.RewardDTO;
import com.appreciateme.reward.model.RewardUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardUtilsTest {

    final String DATE_STRING = "2022-03-09 08:00:00";
    final long DATE_TIMESTAMP = 1646809200000L;

    final Reward REWARD_1 = Reward.builder()
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

    final Reward REWARD_2 = Reward.builder()
            .id("6357c098efc10f0e8db69d20")
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .requiredOpinionAmount(5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom("2022-10-09 08:00:00")
            .dateTo("2022-10-20 08:00:00")
            .availabilityDays(7)
            .build();

    final Reward REWARD_3 = Reward.builder()
            .id("6358da3be4f5493ce09d28d4")
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .requiredOpinionAmount(10)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom("2022-04-09 08:00:00")
            .dateTo("2023-02-09 08:00:00")
            .availabilityDays(14)
            .build();

    final RewardDTO REWARD_DTO_1 = RewardDTO.builder()
            .id("6351508c881167185d79e3d0")
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .requiredOpinionAmount(3)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom(1646809200000L)
            .dateTo(1686290400000L)
            .availabilityDays(30)
            .build();

    final RewardDTO REWARD_DTO_2 = RewardDTO.builder()
            .id("6357c098efc10f0e8db69d20")
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .requiredOpinionAmount(5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom(1665295200000L)
            .dateTo(1666245600000L)
            .availabilityDays(7)
            .build();

    final RewardDTO REWARD_DTO_3 = RewardDTO.builder()
            .id("6358da3be4f5493ce09d28d4")
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .requiredOpinionAmount(10)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom(1649484000000L)
            .dateTo(1675926000000L)
            .availabilityDays(14)
            .build();

    @Test
    @DisplayName("[ 1] given RewardDTO - when mapToReward() - return Reward")
    void givenRewardDTO_whenMapToReward_thenReturnReward() {
        Reward reward = RewardUtils.mapToReward(REWARD_DTO_1);

        assertEquals(REWARD_1, reward);
    }

    @Test
    @DisplayName("[ 2] given Reward - when mapToDto() - return RewardDTO")
    void givenReward_whenMapToDto_thenReturnRewardDTO() {
        RewardDTO rewardDTO = RewardUtils.mapToDto(REWARD_1);

        assertEquals(REWARD_DTO_1, rewardDTO);
    }

    @Test
    @DisplayName("[ 3] given List<Reward> - when mapToDtoList() - return List<RewardDTO>")
    void givenListOfReward_whenMapToDtoList_thenReturnListOfRewardDTO() {
        List<Reward> rewards = List.of(REWARD_1, REWARD_2, REWARD_3);
        List<RewardDTO> rewardDTOs = List.of(REWARD_DTO_1, REWARD_DTO_2, REWARD_DTO_3);

        List<RewardDTO> resultList = RewardUtils.mapToDtoList(rewards);

        assertEquals(rewardDTOs, resultList);
    }

    @Test
    @DisplayName("[ 4] given List<RewardDTO> - when mapToRewardList() - return List<Reward>")
    void givenListOfRewardDTO_whenMapToRewardList_thenReturnListOfReward() {
        List<Reward> rewards = List.of(REWARD_1, REWARD_2, REWARD_3);
        List<RewardDTO> rewardDTOs = List.of(REWARD_DTO_1, REWARD_DTO_2, REWARD_DTO_3);

        List<Reward> resultList = RewardUtils.mapToRewardList(rewardDTOs);

        assertEquals(rewards, resultList);
    }

    @Test
    @DisplayName("[ 5] given long timestamp - when mapLongToStringDate() - return date as String")
    void givenLongTimestamp_whenMapLongToStringDate_thenReturnDateAsString() {
        String result = RewardUtils.mapLongToStringDate(DATE_TIMESTAMP);

        assertEquals(DATE_STRING, result);
    }

    @Test
    @DisplayName("[ 6] given string date - when mapStringDateToLong() - return date as timestamp")
    void givenStringDate_whenMapStringDateToLong_thenReturnDateAsTimestamp() {
        long result = RewardUtils.mapStringDateToLong(DATE_STRING);

        assertEquals(DATE_TIMESTAMP, result);
    }
}
