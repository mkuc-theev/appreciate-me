package com.appreciateme.awarding;

import com.appreciateme.awarding.controller.AwardingRepository;
import com.appreciateme.awarding.controller.AwardingService;
import com.appreciateme.awarding.exception.AwardingNotFoundException;
import com.appreciateme.awarding.model.Awarding;
import com.appreciateme.awarding.model.AwardingDTO;
import com.appreciateme.awarding.model.AwardingUtils;
import com.appreciateme.awarding.model.OwnedReward;
import com.appreciateme.awarding.model.OwnedRewardDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class AwardingServiceTest {

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

    private static final AwardingRepository repository = Mockito.mock(AwardingRepository.class);
    private static final AwardingService service = new AwardingService(repository);

    @Test
    @DisplayName("[ 1] given existing awardings - when getAll() - then return List<AwardingDTO>")
    void givenExistingAwardings_whenGetAll_thenReturnListOfAwardingDTO() {
        final List<AwardingDTO> awardingDTOs = List.of(AWARDING_DTO_1, AWARDING_DTO_2);
        final List<Awarding> awardings = AwardingUtils.mapToAwardingList(awardingDTOs);

        given(repository.findAll())
                .willReturn(awardingDTOs);

        final List<Awarding> result = service.getAll();

        then(repository)
                .should()
                .findAll();

        assertEquals(result, awardings);
    }

    @Test
    @DisplayName("[ 2] given id of existing awarding - when getById() - then return AwardingDTO")
    void givenIdOfExistingAwarding_whenGetById_thenReturnAwardingDTO() {
        final String id = "6351508c881167185d79e3d0";

        given(repository.findById(id))
                .willReturn(Optional.of(AWARDING_DTO_1));

        final Awarding result = service.getById(id);

        then(repository)
                .should()
                .findById(eq(id));

        assertEquals(AWARDING_1, result);
    }

    @Test
    @DisplayName("[ 3] given id of not existing awarding - when getById() - then throw AwardingNotFoundException")
    void givenIdOfNotExistingAwarding_whenGetById_thenThrowAwardingNotFoundException() {
        final String id = "hehe";

        given(repository.findById(eq(id)))
                .willThrow(new AwardingNotFoundException(id));

        assertThrows(
                AwardingNotFoundException.class,
                () -> service.getById(id));
    }
}
