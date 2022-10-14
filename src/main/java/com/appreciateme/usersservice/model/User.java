package com.appreciateme.usersservice.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

  public static boolean isUserCorrect(User user) {
    return user.getId() != null && !user.getId().isEmpty()
        && user.getFirstName() != null && !user.getFirstName().isEmpty()
        && user.getLastName() != null && !user.getLastName().isEmpty()
        && user.getEmail() != null && !user.getEmail().isEmpty()
        && user.getAge() >= 0
        && user.getSex() != null
        && user.getAccountBalance() != null
        && user.getAccountBalance().compareTo(BigDecimal.ZERO) < 0;
  }
}
