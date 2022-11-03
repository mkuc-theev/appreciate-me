package com.appreciateme.usersservice;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.appreciateme.credential.model.Credential;
import com.appreciateme.usersservice.controller.UserController;
import com.appreciateme.usersservice.model.Sex;
import com.appreciateme.usersservice.model.User;
import com.appreciateme.usersservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;


@WebMvcTest(UserController.class)
public class UserControllerTests {

  final String DOMAIN = "/users/";

  final User USER_1 = new User("abaranski", "Adam", "Barański", "abaranski@griddynamics.com", 22,
      Sex.MALE);

  final User USER_2 = new User("atyranski", "Arkadiusz", "Tyranski", "atyranski@griddynamics.com",
      21, Sex.MALE);

  final User USER_3 = new User("pbogdan", "Paweł", "Bogdan", "pbogdan@griddynamics.com", 38,
      Sex.MALE);

  final User USER_4 = new User("mkuc", "Michał", "Kuć", "mkuc@griddynamics.com", 21, Sex.MALE);

  final User INVALID_USER = new User("mariusz", "Mariusz", "", "mmodaodwa@gmail.com", -1, Sex.MALE);


  @Autowired
  MockMvc mockMvc;

  @MockBean
  UserService userService;

  @Autowired
  private RestTemplate mockRestTemplate;
  private MockRestServiceServer mockServer;

  @Autowired
  ObjectMapper mapper;

  @BeforeEach
  public void init() {
    mockServer = MockRestServiceServer.createServer(mockRestTemplate);
  }



  @Test
  void getAllUsers_GET_thenReturnStatusOk() throws Exception {
    final List<User> users = List.of(USER_1, USER_2, USER_3, USER_4);
    final String endpoint = DOMAIN;

    Mockito.when(userService.getAll()).thenReturn(users);

    mockMvc.perform(get(endpoint).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].id").value(USER_1.getId()))
        .andExpect(jsonPath("$[1].id").value(USER_2.getId()))
        .andExpect(jsonPath("$[2].id").value(USER_3.getId()))
        .andExpect(jsonPath("$[3].id").value(USER_4.getId()));
  }

  @Test
  void getUserById_GET_userExists_thenReturnStatusOk() throws Exception {
    final String id = USER_1.getId();
    final String endpoint = String.format("%sfindById?id=%s", DOMAIN, id);
    System.out.printf(endpoint);

    Mockito.when(userService.existsById(id)).thenReturn(true);
    Mockito.when(userService.getById(id)).thenReturn(USER_1);

    mockMvc.perform(get(endpoint).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.id").value(USER_1.getId()))
        .andExpect(jsonPath("$.firstName").value(USER_1.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(USER_1.getLastName()))
        .andExpect(jsonPath("$.email").value(USER_1.getEmail()));
  }

  @Test
  void getUserById_GET_userDoesNotExist_thenReturnStatusNotFound() throws Exception {
    final String id = "wwhite";
    final String endpoint = String.format("%sfindById?id=%s", DOMAIN, id);
    System.out.printf(endpoint);

    Mockito.when(userService.existsById(id)).thenReturn(false);

    mockMvc.perform(get(endpoint)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void getUserByEmail_GET_userExists_thenReturnStatusOk() throws Exception {
    final String email = USER_2.getEmail();
    final String endpoint = String.format("%sfindByEmail?email=%s", DOMAIN, email);

    Mockito.when(userService.existsByEmail(email)).thenReturn(true);
    Mockito.when(userService.getByEmail(email)).thenReturn(USER_2);

    mockMvc.perform(get(endpoint)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.id").value(USER_2.getId()));
  }

  @Test
  void getUserByEmail_GET_userDoesNotExist_thenReturnStatusNotFound() throws Exception {
    final String email = "bembele@gmail.com";
    final String endpoint = String.format("%sfindByEmail?email=%s", DOMAIN, email);

    Mockito.when(userService.existsByEmail(email)).thenReturn(false);

    mockMvc.perform(get(endpoint)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void getUserByBothNames_GET_userExists_thenReturnStatusOk() throws Exception {
    final String firstName = USER_4.getFirstName();
    final String lastName = USER_4.getLastName();
    final String endpoint = String.format("%sfindByName?firstName=%s&lastName=%s", DOMAIN,
        firstName, lastName);

    Mockito.when(userService.getByFirstNameAndLastName(firstName, lastName))
        .thenReturn(List.of(USER_4));

    mockMvc.perform(get(endpoint).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id").value(USER_4.getId()));
  }

  @Test
  void getUserByBothNames_GET_userDoesNotExist_thenReturnStatusNotFound() throws Exception {
    final String firstName = USER_4.getFirstName();
    final String lastName = USER_3.getLastName();
    final String endpoint = String.format("%sfindByName?firstName=%s&lastName=%s", DOMAIN,
        firstName, lastName);

    Mockito.when(userService.getByFirstNameAndLastName(firstName, lastName)).thenReturn(List.of());

    mockMvc.perform(get(endpoint).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void createValidUser_POST_thenReturnStatusCreated() throws Exception {

    final String endpoint = DOMAIN;

    mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8007/credentials/")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withStatus(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON));

    mockMvc.perform(
            post(endpoint).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(USER_1)))
        .andExpect(status().isCreated());
  }

  @Test
  void createInvalidUser_POST_thenReturnStatusBadRequest() throws Exception {

    final String endpoint = DOMAIN;

    mockMvc.perform(
            post(endpoint).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(INVALID_USER)))
        .andExpect(status().isBadRequest());
  }
}
