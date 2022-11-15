package com.appreciateme.awarding;

import com.appreciateme.awarding.model.Awarding;
import com.appreciateme.awarding.model.AwardingDTO;
import com.appreciateme.awarding.model.AwardingUtils;
import com.appreciateme.reward.model.Reward;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.appreciateme.awarding.TestObjects.AWARDING_1;
import static com.appreciateme.awarding.TestObjects.AWARDING_2;
import static com.appreciateme.awarding.TestObjects.AWARDING_DTO_1;
import static com.appreciateme.awarding.TestObjects.AWARDING_DTO_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AwardingUtilsTest {

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
