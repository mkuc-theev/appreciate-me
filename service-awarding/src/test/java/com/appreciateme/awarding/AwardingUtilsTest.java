package com.appreciateme.awarding;

import com.appreciateme.awarding.model.Awarding;
import com.appreciateme.awarding.model.AwardingDTO;
import com.appreciateme.awarding.model.AwardingUtils;
import com.appreciateme.awarding.model.OwnedReward;
import com.appreciateme.awarding.model.OwnedRewardDTO;
import com.appreciateme.reward.model.Reward;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AwardingUtilsTest {

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

    final OwnedReward OWNED_REWARD_1 = OwnedReward.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom("2022-04-01 08:00:00")
            .dateTo("2022-05-01 08:00:00")
            .used(false)
            .build();

    final OwnedReward OWNED_REWARD_2 = OwnedReward.builder()
            .rewardId("6357c098efc10f0e8db69d20")
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom("2022-04-01 08:00:00")
            .dateTo("2022-05-01 08:00:00")
            .used(true)
            .build();

    final OwnedReward OWNED_REWARD_3 = OwnedReward.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom("2022-04-01 08:00:00")
            .dateTo("2022-05-01 08:00:00")
            .used(false)
            .build();

    final OwnedRewardDTO OWNED_REWARD_DTO_1 = OwnedRewardDTO.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom(1648792800000L)
            .dateTo(1651384800000L)
            .used(false)
            .build();

    final OwnedRewardDTO OWNED_REWARD_DTO_2 = OwnedRewardDTO.builder()
            .rewardId("6357c098efc10f0e8db69d20")
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom(1648792800000L)
            .dateTo(1651384800000L)
            .used(true)
            .build();

    final OwnedRewardDTO OWNED_REWARD_DTO_3 = OwnedRewardDTO.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom(1648792800000L)
            .dateTo(1651384800000L)
            .used(false)
            .build();

    final Awarding AWARDING_1 = Awarding.builder()
            .userId("abaranski")
            .rewards(List.of(OWNED_REWARD_1, OWNED_REWARD_2))
            .build();

    final Awarding AWARDING_2 = Awarding.builder()
            .userId("pbogdan")
            .rewards(List.of(OWNED_REWARD_2, OWNED_REWARD_3))
            .build();

    final AwardingDTO AWARDING_DTO_1 = AwardingDTO.builder()
            .userId("abaranski")
            .rewards(List.of(OWNED_REWARD_DTO_1, OWNED_REWARD_DTO_2))
            .build();

    final AwardingDTO AWARDING_DTO_2 = AwardingDTO.builder()
            .userId("pbogdan")
            .rewards(List.of(OWNED_REWARD_DTO_2, OWNED_REWARD_DTO_3))
            .build();

    @Test
    @DisplayName("[ 1] given AwardingDTO - when mapToAwarding() - then return Awarding")
    void givenAwardingDTO_whenMapToAwardingDTO_thenReturnAwarding() {
        Awarding awarding = AwardingUtils.mapToAwarding(AWARDING_DTO_1);

        assertEquals(AWARDING_1, awarding);
    }

    @Test
    @DisplayName("[ 2] given Awarding - when mapToDto() - then return AwardingDTO")
    void givenAwarding_whenMapToDto_thenReturnAwardingDTO() {
        AwardingDTO awardingDTO = AwardingUtils.mapToDto(AWARDING_1);

        assertEquals(AWARDING_DTO_1, awardingDTO);
    }

    @Test
    @DisplayName("[ 3] given List<AwardingDTO> - when mapToAwardingList() - then return List<Awarding>")
    void givenListOfAwardingDTO_whenMapToAwardingList_thenReturnListOfAwarding() {
        List<Awarding> awardings = List.of(AWARDING_1, AWARDING_2);
        List<AwardingDTO> awardingDTOs = List.of(AWARDING_DTO_1, AWARDING_DTO_2);

        List<Awarding> result = AwardingUtils.mapToAwardingList(awardingDTOs);

        assertEquals(awardings, result);
    }

    @Test
    @DisplayName("[ 4] given List<Awarding> - when mapToDtoList() - then return List<AwardingDTO>")
    void givenListOfAwarding_whenMapToDtoList_thenReturnListOfAwardingDTO() {
        List<Awarding> awardings = List.of(AWARDING_1, AWARDING_2);
        List<AwardingDTO> awardingDTOs = List.of(AWARDING_DTO_1, AWARDING_DTO_2);

        List<AwardingDTO> result = AwardingUtils.mapToDtoList(awardings);

        assertEquals(awardingDTOs, result);
    }

}
