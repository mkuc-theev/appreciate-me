package com.appreciateme.usersservice;

import com.appreciateme.usersservice.model.Sex;
import com.appreciateme.usersservice.model.User;
import com.appreciateme.usersservice.repository.UserRepository;
import com.appreciateme.usersservice.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserServiceTests {

  final User USER_1 = new User("abaranski",
      "Adam",
      "Barański",
      "abaranski@griddynamics.com",
      22,
      Sex.MALE);

  final User USER_2 = new User("atyranski",
      "Arkadiusz",
      "Tyranski",
      "atyranski@griddynamics.com",
      21,
      Sex.MALE);

  final User USER_3 = new User("pbogdan",
      "Paweł",
      "Bogdan",
      "pbogdan@griddynamics.com",
      38,
      Sex.MALE);

  final User USER_4 = new User("mkuc",
      "Michał",
      "Kuć",
      "mkuc@griddynamics.com",
      21,
      Sex.MALE);

  final User INVALID_USER = new User("mariusz",
      "Mariusz",
      "",
      "mmodaodwa@gmail.com",
      -1,
      Sex.MALE);
  private final UserRepository userRepository = Mockito.mock(UserRepository.class);
  private final UserService userService = new UserService(userRepository);

  @Test
  void getAllUsers_thenReturnList() {

    final List<User> usersList = List.of(USER_1, USER_2, USER_3, USER_4);

    Mockito.when(userRepository.findAll()).thenReturn(usersList);

    final List<User> usersFromService = userService.getAll();

    Assertions.assertEquals(usersList, usersFromService);
  }

  @Test
  void getUserByMail_UserWasFound_thenReturnValidUser() {

    final String email = USER_2.getEmail();

    Mockito.when(userRepository.findByEmail(email))
        .thenReturn(Optional.of(USER_2));

    final User foundUser = userService.getByEmail(email);
    Assertions.assertEquals(foundUser, USER_2);
  }

  @Test
  void getUserByMail_UserWasNotFound_ThenThrowRuntimeException() {

    final String email = "random_email@mail.com";

    Mockito.when(userRepository.findByEmail(email))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> userService.getByEmail(email));

  }

  @Test
  void getUserById_UserWasFound_thenReturnValidUser() {

    final String id = USER_3.getId();

    Mockito.when(userRepository.findById(id))
        .thenReturn(Optional.of(USER_3));

    final User foundUser = userService.getById(id);
    Assertions.assertEquals(foundUser, USER_3);
  }

  @Test
  void getUserById_UserWasNotFound_ThenThrowRuntimeException() {

    final String id = "takiegoChlopaNieMa";

    Mockito.when(userRepository.findById(id))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(RuntimeException.class, () -> userService.getById(id));

  }


}
