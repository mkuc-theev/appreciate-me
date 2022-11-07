package com.appreciateme.webservice.services;

import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.opinion.model.OpinionUtils;
import com.appreciateme.webservice.MicroserviceData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class OpinionDataServiceImpl implements OpinionDataService {

    @Autowired
    MicroserviceData microserviceData;

    @Override
    public List<Opinion> getAllOpinionsForUser(String id) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getOpinionsURI() + "/opinions/reviewedUser/" + id))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        String response = HttpClient
                .newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                .body();

        TypeReference<List<Opinion>> typeReference = new TypeReference<>() {};

        return new ObjectMapper().readValue(response, typeReference);
    }

    @Override
    public void createOpinionFromForm(Opinion formData) throws IOException, InterruptedException, URISyntaxException {
        OpinionUtils.setCurrentDate(formData);
        System.out.println(new ObjectMapper().writeValueAsString(formData));
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getOpinionsURI() + "/opinions"))
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
    public List<Opinion> getAllOpinionsByUser(String id) throws IOException, InterruptedException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getOpinionsURI() + "/opinions/opinionUser/" + id))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        String response = HttpClient
                .newHttpClient()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                .body();

        TypeReference<List<Opinion>> typeReference = new TypeReference<>() {};

        try {
            return new ObjectMapper().readValue(response, typeReference);
        } catch (MismatchedInputException e) {
            return new ArrayList<>();
        }
    }
}
