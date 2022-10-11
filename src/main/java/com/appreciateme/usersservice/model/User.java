package com.appreciateme.usersservice.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

  @Id
  private String id;
  @NonNull
  private String firstName;
  @NonNull
  private String lastName;
  @NonNull
  private String email;
  private int age;
  private Sex sex;
  private BigDecimal accountBalance;

}
