package com.appreciateme.tagservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class TagRestController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    TagService tagService;

    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<?> getAllTags() {
        List<Tag> tagList = tagService
                .findAll()
                .stream()
                .map(p -> objectMapper.convertValue(p, Tag.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tagList, HttpStatus.OK);
    }

    @GetMapping(value = "/byID")
    @ResponseBody
    public ResponseEntity<?> getTagById(@RequestParam(name = "id") String id) {
        if (tagService.findById(id).isEmpty()) {
            return new ResponseEntity<>("Tag with given ID does not exist.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(objectMapper.convertValue(tagService.findById(id).get(), Tag.class), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveOrUpdateTag(@RequestBody Tag tag) {
        return new ResponseEntity<>(tagService.saveOrUpdateTag(objectMapper.convertValue(tag, TagDTO.class)), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteID")
    public ResponseEntity<?> deleteTagById(@RequestParam(name = "id") String id) {
        tagService.deleteTagById(id);
        return new ResponseEntity<>("Tag with ID: " + id + " deleted successfully.", HttpStatus.OK);
    }
}
