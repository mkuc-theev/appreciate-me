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
