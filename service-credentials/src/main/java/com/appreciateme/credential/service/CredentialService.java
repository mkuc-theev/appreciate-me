package com.appreciateme.credential.service;

import com.appreciateme.credential.model.Credential;
import com.appreciateme.credential.repository.CredentialRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialService {

  private final CredentialRepository repository;

  public void add(Credential toInsert) {
    repository.insert(toInsert);
  }

  public List<Credential> getAll() {
    return repository.findAll();
  }

  public Credential getByEmail(String email) {
    return repository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("No such credentials for given email!"));
  }

  public boolean existsByEmail(String email) {
    return repository.existsByEmail(email);
  }

  public void removeByEmail(String email) {
    repository.deleteByEmail(email);
  }
}
