package com.appreciateme.webservice;

import com.appreciateme.usersservice.model.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface WebService {

    List<User> getAllUsers() throws URISyntaxException, IOException, InterruptedException;

}
