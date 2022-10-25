package com.appreciateme.webservice.services;

import com.appreciateme.usersservice.model.User;
import com.appreciateme.webservice.model.CreateUserFormData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface UserService {

    List<User> getAllUsers() throws URISyntaxException, IOException, InterruptedException;

    void deleteUserFromList(String id) throws URISyntaxException, IOException, InterruptedException;

    void createUserFromForm(CreateUserFormData formData) throws IOException, URISyntaxException, InterruptedException;

}
