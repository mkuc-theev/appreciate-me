package com.appreciateme.usersservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private Sex sex;
    private BigDecimal accountBalance;

}
