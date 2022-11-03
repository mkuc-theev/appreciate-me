package com.appreciateme.credential.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "credential")
public class Credential {

  private String email;
  private String password;
  private Role role;


  public static boolean credentialsCorrect(Credential credential) {
    return credential.getEmail() != null && credential.getEmail().contains("@")
        && credential.getPassword() != null && !credential.getPassword().isEmpty()
        && credential.getRole() != null;
  }
}
