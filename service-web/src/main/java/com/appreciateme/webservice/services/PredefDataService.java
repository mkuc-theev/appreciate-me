package com.appreciateme.webservice.services;

import com.appreciateme.predef.model.PredefMark;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface PredefDataService {

    List<PredefMark> getAllPredefs() throws URISyntaxException, IOException, InterruptedException;

    void createPredefFromForm(PredefMark formData) throws URISyntaxException, IOException, InterruptedException;

    void deletePredefById(String id) throws URISyntaxException, IOException, InterruptedException;

    PredefMark getPredefById(String id) throws URISyntaxException, IOException, InterruptedException;

}
