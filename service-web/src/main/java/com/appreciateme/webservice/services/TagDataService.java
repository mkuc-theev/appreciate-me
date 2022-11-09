package com.appreciateme.webservice.services;

import com.appreciateme.tag.model.Tag;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface TagDataService {
    List<Tag> getAllTags() throws IOException, InterruptedException, URISyntaxException;
    void createTagFromForm(Tag formData) throws IOException, InterruptedException, URISyntaxException;
    void removeTag(String id) throws URISyntaxException, IOException, InterruptedException;
}
