package com.appreciateme.reward;

import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.reward.controller.RewardRepository;
import com.appreciateme.reward.controller.RewardService;
import com.appreciateme.reward.exception.RewardNotFoundException;
import com.appreciateme.reward.model.Reward;
import com.appreciateme.reward.model.RewardCorrectnessStatus;
import com.appreciateme.reward.model.RewardDTO;
import com.appreciateme.reward.model.RewardUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class RewardServiceTest {

    final String DATE_STRING = "2022-03-09 08:00:00";
    final long DATE_TIMESTAMP = 1646809200000L;

    final Reward REWARD_1 = Reward.builder()
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

    private static final RewardRepository repository = Mockito.mock(RewardRepository.class);
    private static final RewardService service = new RewardService(repository);

    @Test
    @DisplayName("[ 1] given existing rewards - when getAll() - then return List<RewardDTO>")
    void givenExistingRewards_whenGetAllRewards_thenReturnListOfRewardDTO() {
        final List<RewardDTO> rewardDTOs = List.of(REWARD_DTO_1, REWARD_DTO_2, REWARD_DTO_3);
        final List<Reward> rewards = RewardUtils.mapToRewardList(rewardDTOs);

        given(repository.findAll())
                .willReturn(rewardDTOs);

        final List<Reward> result = service.getAll();

        then(repository)
                .should()
                .findAll();

        assertEquals(result, rewards);
    }

    @Test
    @DisplayName("[ 2] given id of exisiting reward - when getById() - then return RewardDTO")
    void givenIdOfExistingReward_whenGetById_thenReturnRewardDTO() {
        final String id = "6351508c881167185d79e3d0";
        final Reward expectedReward = RewardUtils.mapToReward(REWARD_DTO_1);

        given(repository.findById(id))
                .willReturn(Optional.of(REWARD_DTO_1));

        Reward result = service.getById(id);

        then(repository)
                .should()
                .findById(id);

        assertEquals(expectedReward, result);
    }

    @Test
    @DisplayName("[ 3] given id of not existing reward - when getById() - then throw RewardNotFoundException")
    void givenIdOfNotExistingReward_whenGetById_thenThrowRewardNotFoundException() {
        final String id = "6357c098eavc0f0e8db69d20";

        given(repository.findById(eq(id)))
                .willThrow(new RewardNotFoundException(id));

        assertThrows(
                RewardNotFoundException.class,
                () -> service.getById(id));
    }

    @Test
    @DisplayName("[ 4] given String with date in correct format - when isDateFormatCorrect - then return true")
    void givenStringWithDateInCorrectFormat_whenIsDateFormatCorrect_thenReturnTrue() {
        final String date = "2022-10-09 08:00:00";
        final boolean result = Reward.isDateFormatCorrect(date);

        assertTrue(result);
    }

    @Test
    @DisplayName("[ 5] given String with date in incorrect format - when isDateFormatCorrect - then return false")
    void givenStringWithDateInIncorrectFormat_whenIsDateFormatCorrect_thenReturnFalse() {
        final String date = "3920-20-40 00:69:68";
        boolean result = Opinion.isDateFormatCorrect(date);

        assertFalse(result);
    }

    @Test
    @DisplayName("[ 6] given correct Reward - when isCorrect - then return CORRECT")
    void givenCorrectReward_whenIsCorrect_thenReturnCORRECT() {
        final RewardCorrectnessStatus result = Reward.isCorrect(REWARD_1);

        assertEquals(RewardCorrectnessStatus.CORRECT, result);
    }

    @Test
    @DisplayName("[ 7] given null Reward - when isCorrect - then return EMPTY_REWARD")
    void givenNullReward_whenIsCorrect_thenReturnEMPTY_REWARD() {
        final RewardCorrectnessStatus result = Reward.isCorrect(null);

        assertEquals(RewardCorrectnessStatus.EMPTY_REWARD, result);
    }

    @Test
    @DisplayName("[ 8] given Reward with empty id - when isCorrect - then return EMPTY_ID")
    void givenRewardWithEmptyId_whenIsCorrect_thenReturnEMPTY_ID() {
        final Reward reward = REWARD_1;
        reward.setId("");

        final RewardCorrectnessStatus result = Reward.isCorrect(reward);

        assertEquals(RewardCorrectnessStatus.EMPTY_ID, result);
    }

    @Test
    @DisplayName("[ 9] given Reward with null companyName - when isCorrect - then return EMPTY_COMPANY_NAME")
    void givenRewardWithNullCompanyName_whenIsCorrect_thenReturnEMPTY_COMPANY_NAME() {
        final Reward reward = REWARD_1;
        reward.setCompanyName(null);

        final RewardCorrectnessStatus result = Reward.isCorrect(reward);

        assertEquals(RewardCorrectnessStatus.EMPTY_COMPANY_NAME, result);
    }

    @Test
    @DisplayName("[10] given Reward with negative value - when isCorrect - then return NEGATIVE_VALUE")
    void givenRewardWithNegativeValue_whenIsCorrect_thenReturnNEGATIVE_VALUE() {
        final Reward reward = REWARD_1;
        reward.setValue(-0.5);

        final RewardCorrectnessStatus result = Reward.isCorrect(reward);

        assertEquals(RewardCorrectnessStatus.NEGATIVE_VALUE, result);
    }

    @Test
    @DisplayName("[11] given Reward with nonpositive requiredOpinionAmount - when isCorrect - then return NONPOSITIVE_REQUIRED_OPINION_AMOUNT")
    void givenRewardWithNonpositiveRequiredOpinionAmount_whenIsCorrect_thenReturnNONPOSITIVE_REQUIRED_OPINION_AMOUNT() {
        final Reward reward = REWARD_1;
        reward.setRequiredOpinionAmount(0);

        final RewardCorrectnessStatus result = Reward.isCorrect(reward);

        assertEquals(RewardCorrectnessStatus.NONPOSITIVE_REQUIRED_OPINION_AMOUNT, result);
    }

    @Test
    @DisplayName("[12] given Reward with null description - when isCorrect - then return EMPTY_DESCRIPTION")
    void givenRewardWithNullDescription_whenIsCorrect_thenReturnEMPTY_DESCRIPTION() {
        final Reward reward = REWARD_1;
        reward.setDescription(null);

        final RewardCorrectnessStatus result = Reward.isCorrect(reward);

        assertEquals(RewardCorrectnessStatus.EMPTY_DESCRIPTION, result);
    }

    @Test
    @DisplayName("[13] given Reward with incorrect dates - when isCorrect - then return FROM_EARLIER_THAN_TO")
    void givenRewardWithIncorrectDates_whenIsCorrect_thenReturnFROM_EARLIER_THAN_TO() {
        final Reward reward = REWARD_1;
        reward.setDateFrom("2022-03-09 08:00:00");
        reward.setDateTo("2022-03-01 08:00:00");

        final RewardCorrectnessStatus result = Reward.isCorrect(reward);

        assertEquals(RewardCorrectnessStatus.FROM_EARLIER_THAN_TO, result);
    }

    @Test
    @DisplayName("[11] given Reward with nonpositive availabilityDays - when isCorrect - then return NONPOSITIVE_AVAILABILITY_DAYS")
    void givenRewardWithNonpositiveAvailabilityDays_whenIsCorrect_thenReturnNONPOSITIVE_AVAILABILITY_DAYS() {
        final Reward reward = REWARD_1;
        reward.setAvailabilityDays(0);

        final RewardCorrectnessStatus result = Reward.isCorrect(reward);

        assertEquals(RewardCorrectnessStatus.NONPOSITIVE_AVAILABILITY_DAYS, result);
    }

}
