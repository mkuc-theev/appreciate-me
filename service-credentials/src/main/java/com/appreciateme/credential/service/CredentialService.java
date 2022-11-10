package com.appreciateme.credential.service;

import com.appreciateme.credential.model.Credential;
import java.util.List;

public interface CredentialService {

  /**
   * Insert new credentials
   *
   * @param toAdd opinion to be added
   */
  void add(Credential toAdd);

  /**
   * Get a list of all credentials
   *
   * @return List of all credentials
   */
  List<Credential> getAll();

  /**
   * Get credentials by email
   *
   * @param email email associated to credentials
   * @return Credential object
   */
  Credential getByEmail(String email);

  /**
   * Check whether credentials with given mail exists
   *
   * @param email email associated to credentials
   * @return true if credential with given email exists, false otherwise
   */
  boolean existsByEmail(String email);

  /**
   * Remove credentials associated with given mail
   *
   * @param email email associated to credentials
   */
  void removeByEmail(String email);
}
