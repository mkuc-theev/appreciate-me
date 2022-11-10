package com.appreciateme.credential.encryption;

import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

@Component
public class CredentialsEventListener extends AbstractMongoEventListener<Object> {

  private final List<String> notToEncrypt = List.of("_id", "_class", "email");
  @Autowired
  EncryptionUtil encryptionUtil;

  @Override
  public void onBeforeSave(BeforeSaveEvent<Object> event) {
    Document eventObject = event.getDocument();

    System.out.println(eventObject);

    if (eventObject != null) {
      for (String key : eventObject.keySet()) {
        if (!notToEncrypt.contains(key)) {
          eventObject.put(key, encryptionUtil.encrypt(eventObject.get(key).toString()));
        }
      }
    }

    super.onBeforeSave(event);
  }

  @Override
  public void onAfterLoad(AfterLoadEvent<Object> event) {
    Document eventObject = event.getDocument();
    System.out.println(eventObject);

    if (eventObject != null) {
      for (String key : eventObject.keySet()) {
        if (!notToEncrypt.contains(key)) {
          eventObject.put(key, encryptionUtil.decrypt(eventObject.get(key).toString()));
        }
      }
    }
  }

}
