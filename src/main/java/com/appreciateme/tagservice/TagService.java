package com.appreciateme.tagservice;

import java.util.List;
import java.util.Optional;

public interface TagService {

    Optional<TagDTO> findById(String id);

    List<TagDTO> findAll();

    String saveOrUpdateTag(TagDTO tagDTO);

    void deleteTagById(String id);
}
