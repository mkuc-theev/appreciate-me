package com.appreciateme.webservice;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class WebServiceImpl implements WebService {

    @Autowired
    MicroserviceData microserviceData;
    @Override
    public List<JSONObject> getAllUsers() throws URISyntaxException {
        HttpRequest httpRequest = HttpRequest
                .newBuilder(new URI(microserviceData.getUserURI() + "/users/"))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        HttpResponse<String> httpResponse = HttpClient.newBuilder().build().send()
        return null;
    }
}
