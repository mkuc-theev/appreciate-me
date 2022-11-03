package com.appreciateme.webservice.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicroserviceData {

  @Value("${services.users.host}")
  private String usersHost;
  @Value("${services.users.port}")
  private String usersPort;
  @Value("${services.opinions.host}")
  private String opinionsHost;
  @Value("${services.opinions.port}")
  private String opinionsPort;
  @Value("${services.predefs.host}")
  private String predefsHost;
  @Value("${services.predefs.port}")
  private String predefsPort;
  @Value("${services.notifications.host}")
  private String notificationsHost;
  @Value("${services.notifications.port}")
  private String notificationsPort;
  @Value("${services.tags.host}")
  private String tagsHost;
  @Value("${services.tags.port}")
  private String tagsPort;
  @Value("${services.credentials.host}")
  private String credentialsHost;
  @Value("${services.credentials.port}")
  private String credentialsPort;

  public String getUsersURI() {
    return "http://%s:%s".formatted(usersHost, usersPort);
  }

  public String getOpinionsURI() {
    return "http://%s:%s".formatted(opinionsHost, opinionsPort);
  }

  public String getPredefsURI() {
    return "http://%s:%s".formatted(predefsHost, predefsPort);
  }

  public String getNotificationsURI() {
    return "http://%s:%s".formatted(notificationsHost, notificationsPort);
  }

  public String getTagsURI() {
    return "http://%s:%s".formatted(tagsHost, tagsPort);
  }

  public String getCredentialsURI() {
    return "http://%s:%s".formatted(credentialsHost, credentialsPort);
  }
}
