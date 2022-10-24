package com.appreciateme.tagservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;


    @Override
    public Optional<TagDTO> findById(String id) {
        return tagRepository.findById(id);
    }

    @Override
    public List<TagDTO> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public String saveOrUpdateTag(TagDTO tagDTO) {
        return tagRepository.save(tagDTO).getId();
    }

    @Override
    public void deleteTagById(String id) {
        tagRepository.deleteById(id);
    }
}
