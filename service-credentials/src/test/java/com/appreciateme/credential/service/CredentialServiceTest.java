package com.appreciateme.credential.service;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.appreciateme.credential.model.Credential;
import com.appreciateme.credential.model.Role;
import com.appreciateme.credential.repository.CredentialRepository;
import com.appreciateme.credential.service.CredentialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class CredentialServiceTest {

  private final Credential SAMPLE_1 = new Credential("abaranski@griddynamics.com", "4b12ui421984",
      Role.USER);

  private final Credential SAMPLE_2 = new Credential("pbogdan@griddynamics.com", "h421o9t214",
      Role.USER);

  private final Credential SAMPLE_3 = new Credential("atyranski@griddynamics.com",
      "h48912g414g1vaa",
      Role.USER);

  private final Credential SAMPLE_4 = new Credential("kucm@griddynamics.com", "kda9wdu932eh13u20",
      Role.USER);

  private final List<Credential> credentialDatabase = List.of(SAMPLE_1, SAMPLE_2, SAMPLE_3,
      SAMPLE_4);

  private final CredentialRepository repository = Mockito.mock(CredentialRepository.class);

  private final CredentialService credentialService = new CredentialService(repository);



  @Test
  void getAllUsers_thenReturnCorrectList() {

    Mockito.when(repository.findAll()).thenReturn(credentialDatabase);

    final List<Credential> credentialsFromService = credentialService.getAll();

    Assertions.assertEquals(credentialsFromService, credentialDatabase);

  }

  @Test
  void getCredentialByMail_mailNotFound_thenThrowExceptionOptional() {

    final String email = "wrongemailexample@gmail.com";

    Mockito.when(repository.findByEmail(email))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> credentialService.getByEmail(email));
  }

  @Test
  void getCredentialByMail_mailFound_thenReturn() {

    final String email = "abaranski@griddynamics.com";

    Mockito.when(repository.findByEmail(email))
        .thenReturn(Optional.of(SAMPLE_1));

    Assertions.assertEquals(credentialService.getByEmail(email), SAMPLE_1);
  }



}