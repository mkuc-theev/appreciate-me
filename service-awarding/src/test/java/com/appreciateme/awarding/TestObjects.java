package com.appreciateme.awarding;

import com.appreciateme.awarding.model.Awarding;
import com.appreciateme.awarding.model.AwardingDTO;
import com.appreciateme.awarding.model.OwnedReward;
import com.appreciateme.awarding.model.OwnedRewardDTO;

import java.util.List;

public class TestObjects {

    public static final OwnedReward OWNED_REWARD_1 = OwnedReward.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom("2022-04-01 08:00:00")
            .dateTo("2022-05-01 08:00:00")
            .used(false)
            .build();

    public static final OwnedReward OWNED_REWARD_2 = OwnedReward.builder()
            .rewardId("6357c098efc10f0e8db69d20")
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom("2022-04-01 08:00:00")
            .dateTo("2022-05-01 08:00:00")
            .used(true)
            .build();

    public static final OwnedReward OWNED_REWARD_3 = OwnedReward.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom("2022-04-01 08:00:00")
            .dateTo("2022-05-01 08:00:00")
            .used(false)
            .build();

    public static final OwnedRewardDTO OWNED_REWARD_DTO_1 = OwnedRewardDTO.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Tektura Cafe")
            .isService(true)
            .value(1.0)
            .description("Darmowe szkolenie Latte Art")
            .dateFrom(1648792800000L)
            .dateTo(1651384800000L)
            .used(false)
            .build();

    public static final OwnedRewardDTO OWNED_REWARD_DTO_2 = OwnedRewardDTO.builder()
            .rewardId("6357c098efc10f0e8db69d20")
            .companyName("Żabka")
            .isService(false)
            .value(0.5)
            .description("Duży hot-dog za 50% taniej!")
            .dateFrom(1648792800000L)
            .dateTo(1651384800000L)
            .used(true)
            .build();

    public static final OwnedRewardDTO OWNED_REWARD_DTO_3 = OwnedRewardDTO.builder()
            .rewardId("6351508c881167185d79e3d0")
            .companyName("Castorama")
            .isService(false)
            .value(0.75)
            .description("Taker firmy LOOOL za 75% taniej!")
            .dateFrom(1648792800000L)
            .dateTo(1651384800000L)
            .used(false)
            .build();

    public static final Awarding AWARDING_1 = Awarding.builder()
            .userId("abaranski")
            .rewards(List.of(OWNED_REWARD_1, OWNED_REWARD_2))
            .build();

    public static final Awarding AWARDING_2 = Awarding.builder()
            .userId("pbogdan")
            .rewards(List.of(OWNED_REWARD_2, OWNED_REWARD_3))
            .build();

    public static final AwardingDTO AWARDING_DTO_1 = AwardingDTO.builder()
            .userId("abaranski")
            .rewards(List.of(OWNED_REWARD_DTO_1, OWNED_REWARD_DTO_2))
            .build();

    public static final AwardingDTO AWARDING_DTO_2 = AwardingDTO.builder()
            .userId("pbogdan")
            .rewards(List.of(OWNED_REWARD_DTO_2, OWNED_REWARD_DTO_3))
            .build();
}
