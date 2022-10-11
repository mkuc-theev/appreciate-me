package com.appreciateme.notificationservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.SECONDS;

public class NotificationData {
    private final String receivingEmail;
    private final String receivingFirstName;
    private final String receivingLastName;
    private String contextFirstName = null;
    private String contextLastName = null;

    NotificationData(String receivingEmail, String contextEmail)
            throws URISyntaxException, InterruptedException, IOException {
        HttpRequest receivingRequest = HttpRequest
                .newBuilder(new URI("http://localhost:8001/users/findByEmail?email=" + receivingEmail))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();
        HttpRequest contextRequest = HttpRequest
                .newBuilder(new URI("http://localhost:8001/users/findByEmail?email=" + contextEmail))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        HttpResponse<String> receivingResponse = HttpClient.newBuilder()
                .build()
                .send(receivingRequest, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> contextResponse = HttpClient.newBuilder()
                .build()
                .send(contextRequest, HttpResponse.BodyHandlers.ofString());

        Map<String, Object> receivingResponseBody = new ObjectMapper().
                readValue(receivingResponse.body(), HashMap.class);

        Map<String, Object> contextResponseBody = new ObjectMapper().
                readValue(contextResponse.body(), HashMap.class);

        this.receivingEmail = receivingEmail;
        receivingFirstName = (String) receivingResponseBody.get("firstName");
        receivingLastName = (String) receivingResponseBody.get("lastName");

        contextFirstName = (String) contextResponseBody.get("firstName");
        contextLastName = (String) contextResponseBody.get("lastName");
    }

    NotificationData(String receivingEmail)
            throws URISyntaxException, InterruptedException, IOException {
        HttpRequest receivingRequest = HttpRequest
                .newBuilder(new URI("localhost:8001/users/findByEmail?email=" + receivingEmail))
                .timeout(Duration.of(10, SECONDS))
                .GET()
                .build();

        HttpResponse<String> receivingResponse = HttpClient.newBuilder()
                .build()
                .send(receivingRequest, HttpResponse.BodyHandlers.ofString());

        Map<String, Object> receivingResponseBody = new ObjectMapper().
                readValue(receivingResponse.body(), HashMap.class);

        this.receivingEmail = receivingEmail;
        receivingFirstName = (String) receivingResponseBody.get("firstName");
        receivingLastName = (String) receivingResponseBody.get("lastName");
    }

    public String getContextFirstName() {
        return contextFirstName;
    }

    public String getContextLastName() {
        return contextLastName;
    }

    public String getReceivingEmail() {
        return receivingEmail;
    }

    public String getReceivingFirstName() {
        return receivingFirstName;
    }

    public String getReceivingLastName() {
        return receivingLastName;
    }
}
