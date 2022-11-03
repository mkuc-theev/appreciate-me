package com.appreciateme.webservice.security;

import com.appreciateme.credential.model.Credential;
import com.appreciateme.webservice.MicroserviceData;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class SecurityUserDetailsService implements UserDetailsService {

  private final String findByEmail = "/credentials/findByEmail?email=";
  @Autowired
  MicroserviceData microserviceData;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    String endpoint = microserviceData.getCredentialsURI() + findByEmail + username;
    System.out.println(endpoint);
    RestTemplate restTemplate = new RestTemplate();

    try {
      Credential credential = restTemplate.getForObject(endpoint, Credential.class);
      System.out.println(credential);
      return new org.springframework.security.core.userdetails.User(credential.getEmail(),
          "{noop}" + credential.getPassword(), true,
          true, true, true,
          List.of());

    } catch (RestClientException e) {
      System.out.println(e);
      return org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
          .username("ADMIN").password("123").roles("ADMIN").build();
    }
  }
}
