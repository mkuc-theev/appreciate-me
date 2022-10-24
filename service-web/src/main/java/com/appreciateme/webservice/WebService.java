package com.appreciateme.webservice;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

public interface WebService {

    List<JSONObject> getAllUsers() throws URISyntaxException;

}
