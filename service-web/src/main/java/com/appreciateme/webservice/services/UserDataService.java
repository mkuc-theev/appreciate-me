package com.appreciateme.webservice.services;

import com.appreciateme.usersservice.model.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface UserDataService {

    List<User> getAllUsers() throws URISyntaxException, IOException, InterruptedException;

    void deleteUserFromList(String id) throws URISyntaxException, IOException, InterruptedException;

    void createUserFromForm(User formData) throws IOException, URISyntaxException, InterruptedException;

    User getUserById(String Id) throws URISyntaxException, IOException, InterruptedException;

}
