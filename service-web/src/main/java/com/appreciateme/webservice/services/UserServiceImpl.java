package com.appreciateme.webservice.services;

import com.appreciateme.usersservice.model.User;
import com.appreciateme.webservice.model.CreateUserFormData;
import com.appreciateme.webservice.model.MicroserviceData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    MicroserviceData microserviceData;


    @Override
    public List<User> getAllUsers() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getUsersURI() + "/users/"))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        HttpResponse<String> httpResponse = HttpClient
                .newBuilder()
                .build()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};

        return new ObjectMapper().readValue(httpResponse.body(), typeReference);
    }

    @Override
    public void deleteUserFromList(String id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getUsersURI() + "/users/" + id))
                .timeout(Duration.of(10, SECONDS))
                .DELETE()
                .build();

        HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public void createUserFromForm(CreateUserFormData formData) throws IOException, URISyntaxException, InterruptedException {
        System.out.println(new ObjectMapper().writeValueAsString(formData));
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getUsersURI() + "/users/"))
                .timeout(Duration.of(10, SECONDS))
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(new ObjectMapper()
                                .writeValueAsString(formData)))
                .header("Content-Type", "application/json")
                .build();

        HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }


}
