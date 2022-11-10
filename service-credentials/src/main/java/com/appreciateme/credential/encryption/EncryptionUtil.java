package com.appreciateme.credential.encryption;

import java.util.Objects;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

  private final PBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
  @Value("${encryption.database.privateKey}")
  private String privateKey;

  public EncryptionUtil() {
    System.out.println(privateKey);
    textEncryptor.setPassword(Objects.requireNonNullElse(privateKey, "default_password"));
  }

  public String encrypt(String toEncrypt) {
    return this.textEncryptor.encrypt(toEncrypt);
  }

  public String decrypt(String toDecrypt) {
    return this.textEncryptor.decrypt(toDecrypt);
  }
}
