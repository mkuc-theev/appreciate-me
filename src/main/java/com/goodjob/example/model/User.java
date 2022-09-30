package com.goodjob.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Document
@Builder
public class User {

    @Id
    private String id;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    private int age;
    private Sex sex;
    @Field("account_balance")
    private BigDecimal accountBalance;
}
