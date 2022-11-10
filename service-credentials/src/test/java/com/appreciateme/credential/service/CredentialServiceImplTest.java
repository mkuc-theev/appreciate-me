package com.appreciateme.credential.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.appreciateme.credential.model.Credential;
import com.appreciateme.credential.model.Role;
import com.appreciateme.credential.repository.CredentialRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CredentialServiceImplTest {

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

  private final CredentialServiceImpl credentialServiceImpl = new CredentialServiceImpl(repository);

  @DisplayName("Test of getAll() method of CredentialService - return list of all credentials")
  @Test
  void getAllUsers_thenReturnCorrectList() {

    Mockito.when(repository.findAll()).thenReturn(credentialDatabase);

    final List<Credential> credentialsFromService = credentialServiceImpl.getAll();

    Assertions.assertEquals(credentialsFromService, credentialDatabase);

  }

  @DisplayName("[1 ] given invalid email - when credentialService.findByEmail(email) - then throw RuntimeException")
  @Test
  void getCredentialByEmail_emailNotFound_thenThrowRuntimeException() {

    final String email = "wrongemailexample@gmail.com";

    Mockito.when(repository.findByEmail(email))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> credentialServiceImpl.getByEmail(email))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("No such credentials");

  }

  @DisplayName("[3 ] given correct email - when credentialService.findByEmail(email) - then return credential")
  @Test
  void getCredentialByMail_mailFound_thenReturnCredentials() {

    final String email = "abaranski@griddynamics.com";

    Mockito.when(repository.findByEmail(email))
        .thenReturn(Optional.of(SAMPLE_1));

    Assertions.assertEquals(credentialServiceImpl.getByEmail(email), SAMPLE_1);
  }


}