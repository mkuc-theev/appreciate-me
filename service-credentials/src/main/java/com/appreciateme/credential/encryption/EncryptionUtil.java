package com.appreciateme.credential.encryption;

import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

  private final PBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();

  private String privateKey;

  public EncryptionUtil() {

  }

  public void setPrivateKey(@Value("${encryption.database.privateKey}") String privateKey) {
    textEncryptor.setPassword(privateKey);
    this.privateKey = privateKey;
  }

  public String encrypt(String toEncrypt) {
    return this.textEncryptor.encrypt(toEncrypt);
  }

  public String decrypt(String toDecrypt) {
    return this.textEncryptor.decrypt(toDecrypt);
  }
}
