package com.appreciateme.credential.model;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "credential")
public class Credential {

  private static String PASSWORD_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
  private static int DEFAULT_PASSWORD_LENGTH = 16;


  private String email;
  private String password;
  private Role role;


  public static boolean credentialsCorrect(Credential credential) {
    return credential.getEmail() != null && credential.getEmail().contains("@")
        && credential.getPassword() != null && !credential.getPassword().isEmpty()
        && credential.getRole() != null;
  }

  public static String generateDefaultLengthRandomPassword() {
    StringBuilder builder = new StringBuilder();

    while (builder.length() < DEFAULT_PASSWORD_LENGTH) {
      builder.append(
          PASSWORD_CHARACTERS.charAt(new Random().nextInt(PASSWORD_CHARACTERS.length())));
    }
    System.out.println(builder);
    return builder.toString();
  }

}
