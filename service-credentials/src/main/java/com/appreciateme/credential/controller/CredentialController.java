package com.appreciateme.credential.controller;

import com.appreciateme.credential.model.Credential;
import com.appreciateme.credential.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
public class CredentialController {

  @Autowired
  private CredentialService service;


  @PostMapping("/")
  public ResponseEntity<?> add(@RequestBody Credential toAdd) {
    if (!Credential.credentialsCorrect(toAdd)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    if (service.existsByEmail(toAdd.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    service.add(toAdd);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping("/findByEmail")
  public ResponseEntity<?> findByEmail(@RequestParam String email) {
    if (!service.existsByEmail(email)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } else {
      return ResponseEntity.ok(service.getByEmail(email));
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<?> deleteByEmail(@RequestParam String email) {
    if (!service.existsByEmail(email)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } else {
      service.removeByEmail(email);
      return ResponseEntity.ok().build();
    }
  }
}
