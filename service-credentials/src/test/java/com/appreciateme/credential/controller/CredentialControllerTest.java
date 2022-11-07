package com.appreciateme.credential.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.appreciateme.credential.model.Credential;
import com.appreciateme.credential.model.Role;
import com.appreciateme.credential.service.CredentialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CredentialController.class)
class CredentialControllerTest {

  private final Credential SAMPLE_1 = new Credential("abaranski@griddynamics.com", "4b12ui421984",
      Role.USER);

  private final Credential SAMPLE_2 = new Credential("pbogdan@griddynamics.com", "h421o9t214",
      Role.USER);

  private final Credential SAMPLE_3 = new Credential("atyranski@griddynamics.com",
      "h48912g414g1vaa",
      Role.USER);

  private final Credential SAMPLE_4 = new Credential("kucm@griddynamics.com", "kda9wdu932eh13u20",
      Role.USER);

  private final Credential INVALID_SAMPLE_MAIL_WITHOUT_AT_SIGN = new Credential("random_mail", null,
      Role.USER);

  private final Credential INVALID_SAMPLE_MAIL_NULL = new Credential(null, null,
      Role.USER);

  private final Credential INVALID_SAMPLE_PASSWORD_NULL = new Credential("validmail@gmail.com",
      null,
      Role.USER);

  private final Credential INVALID_SAMPLE_PASSWORD_EMPTY = new Credential("validmail@gmail.com", "",
      Role.USER);

  private final Credential VALID_SAMPLE = new Credential("validmail@gmail.com", "validpassword",
      Role.USER);

  private final List<Credential> credentialDatabase = List.of(SAMPLE_1, SAMPLE_2, SAMPLE_3,
      SAMPLE_4);
  private final String DOMAIN = "/credentials/";
  private final String BY_EMAIL = "findByEmail?email=";
  @Autowired
  MockMvc mockMvc;
  @MockBean
  CredentialService service;
  @Autowired
  ObjectMapper mapper;

  @DisplayName("[1 ] given List<Credential> - when GET /credentials/ - then return List<Credential> and status Ok.")
  @Test
  void getAllCredentials_GET_thenReturnStatusOk() throws Exception {
    String endpoint = DOMAIN;

    Mockito.when(service.getAll()).thenReturn(credentialDatabase);

    mockMvc.perform(get(endpoint).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0]").value(SAMPLE_1))
        .andExpect(jsonPath("$[1]").value(SAMPLE_2))
        .andExpect(jsonPath("$[2]").value(SAMPLE_3))
        .andExpect(jsonPath("$[3]").value(SAMPLE_4));
  }

  @DisplayName("[2 ] given not existing email - when GET /credentials/findByEmail - then return status NOT FOUND.")
  @Test
  void getCredentialByInvalidEmail_GET_thenReturnStatusNotFound() throws Exception {
    String invalidEmail = "takiegomailaniema@gmail.com";
    String endpoint = DOMAIN + BY_EMAIL + invalidEmail;

    Mockito.when(service.existsByEmail(invalidEmail)).thenReturn(false);

    mockMvc.perform(get(endpoint).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @DisplayName("[3 ] given correct email - when GET /credentials/findByEmail - then return Credential and status OK")
  @Test
  void getCredentialByExistingEmail_GET_thenReturnStatusOk() throws Exception {
    String email = SAMPLE_1.getEmail();
    String endpoint = DOMAIN + BY_EMAIL + email;

    Mockito.when(service.existsByEmail(email)).thenReturn(true);
    Mockito.when(service.getByEmail(email)).thenReturn(SAMPLE_1);

    mockMvc.perform(get(endpoint).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(SAMPLE_1));
  }


  @DisplayName("[4 ] given invalid email - when POST /credentials/ - then return status BAD REQUEST")
  @Test
  void addCredentialWithInvalidMail_POST_thenReturnStatusBadRequest() throws Exception {

    mockMvc.perform(
            post(DOMAIN).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(INVALID_SAMPLE_MAIL_WITHOUT_AT_SIGN)))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("[5 ] given credential with null mail - when POST /credentials/ - then return status BAD REQUEST.")
  @Test
  void addCredentialWithNullMail_POST_thenReturnStatusBadRequest() throws Exception {

    mockMvc.perform(
            post(DOMAIN).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(INVALID_SAMPLE_MAIL_NULL)))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("[6 ] given credential with null password - when POST /credentials/ - then return status BAD REQUEST.")
  @Test
  void addCredentialWithNullPassword_POST_thenReturnStatusBadRequest() throws Exception {

    mockMvc.perform(
            post(DOMAIN).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(INVALID_SAMPLE_PASSWORD_NULL)))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("[7 ] given credential with empty password - when POST /credentials/ - then return status BAD REQUEST.")
  @Test
  void addCredentialWithEmptyPassword_POST_thenReturnStatusBadRequest() throws Exception {

    mockMvc.perform(
            post(DOMAIN).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(INVALID_SAMPLE_PASSWORD_EMPTY)))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("[8 ] given credential with correct data - when POST /credentials/ - then return status CREATED.")
  @Test
  void addValidCredential_POST_thenReturnStatusCreated() throws Exception {

    mockMvc.perform(post(DOMAIN).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(VALID_SAMPLE)))
        .andExpect(status().isCreated());

  }

  @DisplayName("[9 ] given credential with valid data but with already occupied email - when POST /credentials/ - then return status CONFLICT.")
  @Test
  void addValidCredentials_POST_emailAlreadyOccupied() throws Exception {

    Mockito.when(service.existsByEmail(SAMPLE_1.getEmail())).thenReturn(true);

    mockMvc.perform(post(DOMAIN).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(SAMPLE_1)))
        .andExpect(status().isConflict());
  }


}