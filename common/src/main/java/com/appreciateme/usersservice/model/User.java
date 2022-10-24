package com.appreciateme.usersservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
@Document(collection = "user")
public class User {

  @Id
  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private int age;
  private Sex sex;
  private BigDecimal accountBalance;

  /**
   * Check if given user has all necessary information.
   *
   * @param user Object of User class to be checked
   * @return true if each field of an object satisfies the constraints, false otherwise
   */
  public static boolean isUserCorrect(User user) {
    return user.getFirstName() != null && !user.getFirstName().isEmpty()
        && user.getLastName() != null && !user.getLastName().isEmpty()
        && user.getEmail() != null && !user.getEmail().isEmpty()
        && user.getAge() >= 0
        && user.getSex() != null
        && user.getAccountBalance() != null
        && user.getAccountBalance().compareTo(BigDecimal.ZERO) > 0;
  }
}
