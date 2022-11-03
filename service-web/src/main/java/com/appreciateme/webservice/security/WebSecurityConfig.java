package com.appreciateme.webservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    String[] staticResources = {
        "/css/**"
    };

    String[] startupPages = {
        "/",
        "/start"
    };

    http
        .authorizeHttpRequests((requests) -> requests
            .antMatchers(staticResources).permitAll()
            .antMatchers(startupPages).permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults()
        );

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new SecurityUserDetailsService();
  }
}
