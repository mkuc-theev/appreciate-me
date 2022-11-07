package com.appreciateme.webservice.services;

import com.appreciateme.opinion.model.Opinion;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface OpinionDataService {
    List<Opinion> getAllOpinionsForUser(String id) throws IOException, InterruptedException, URISyntaxException;

    void createOpinionFromForm(Opinion formData) throws IOException, InterruptedException, URISyntaxException;

    List<Opinion> getAllOpinionsByUser(String id) throws IOException, InterruptedException, URISyntaxException;

}
