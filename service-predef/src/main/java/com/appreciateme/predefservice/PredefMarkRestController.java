package com.appreciateme.predefservice;

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
@RequestMapping("/predefs")
public class PredefMarkRestController {

    @Autowired
    private PredefMarkService predefMarkService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/")
    @ResponseBody
    public ResponseEntity<?> getAllPredefs() {
        List<PredefMark> predefMarkList = predefMarkService
                .findAll()
                .stream()
                .map(p -> objectMapper.convertValue(p, PredefMark.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(predefMarkList, HttpStatus.OK);
    }

    @GetMapping(value = "/byID")
    @ResponseBody
    public ResponseEntity<?> getPredefById(@RequestParam(name = "id") String id) {
        return new ResponseEntity<>(objectMapper.convertValue(predefMarkService.findByID(id).get(), PredefMark.class), HttpStatus.OK);
    }

    @GetMapping(value = "/byName")
    @ResponseBody
    public ResponseEntity<?> getPredefByName(@RequestParam(name = "name") String name) {
        return new ResponseEntity<>(objectMapper.convertValue(predefMarkService.findByName(name).get(), PredefMark.class), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveOrUpdatePredef(@RequestBody PredefMark predefMark) {
        return new ResponseEntity(predefMarkService
                .saveOrUpdatePredef(objectMapper
                        .convertValue(predefMark, PredefMarkDTO.class))
                .getId(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteID")
    public ResponseEntity<?> deletePredefById(@RequestParam(name = "id") String id) {
        predefMarkService.deletePredefByID(id);
        return new ResponseEntity("Template deleted successfully.", HttpStatus.OK);
    }
}
