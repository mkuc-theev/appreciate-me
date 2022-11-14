package com.appreciateme.webservice.services;

import com.appreciateme.predef.model.PredefMark;
import com.appreciateme.webservice.MicroserviceData;
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
public class PredefDataServiceImpl implements PredefDataService {
    
    @Autowired
    MicroserviceData microserviceData;

    @Override
    public List<PredefMark> getAllPredefs() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getPredefsURI() + "/predefs/"))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        String response = HttpClient
                .newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                .body();

        TypeReference<List<PredefMark>> typeReference = new TypeReference<>() {};

        return new ObjectMapper().readValue(response, typeReference);
    }

    @Override
    public void createPredefFromForm(PredefMark formData) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getPredefsURI() + "/predefs/save"))
                .timeout(Duration.of(10, SECONDS))
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(new ObjectMapper()
                                .writeValueAsString(formData)))
                .header("Content-Type", "application/json")
                .build();

        HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public void deletePredefById(String id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getPredefsURI() + "/predefs/deleteID?id=" + id))
                .timeout(Duration.of(10, SECONDS))
                .DELETE()
                .build();

        HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());

    }

    @Override
    public PredefMark getPredefById(String id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getPredefsURI() + "/predefs/byID?id=" + id))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        String response = HttpClient
                .newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                .body();

        TypeReference<PredefMark> typeReference = new TypeReference<>() {};

        return new ObjectMapper().readValue(response, typeReference);
    }
}