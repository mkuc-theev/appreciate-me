package com.appreciateme.awarding;

import com.appreciateme.awarding.controller.AwardingRepository;
import com.appreciateme.awarding.controller.AwardingService;
import com.appreciateme.awarding.exception.AwardingNotFoundException;
import com.appreciateme.awarding.model.Awarding;
import com.appreciateme.awarding.model.AwardingDTO;
import com.appreciateme.awarding.model.AwardingUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static com.appreciateme.awarding.TestObjects.AWARDING_1;
import static com.appreciateme.awarding.TestObjects.AWARDING_DTO_1;
import static com.appreciateme.awarding.TestObjects.AWARDING_DTO_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class AwardingServiceTest {

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
