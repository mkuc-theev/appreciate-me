package com.appreciateme.usersservice.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document
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
