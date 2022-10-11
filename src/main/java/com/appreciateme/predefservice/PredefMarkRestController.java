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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/predefs")
public class PredefMarkRestController {

    @Autowired
    private PredefMarkService predefMarkService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/")
    public List<PredefMarkDTO> getAllPredefs() {
        return predefMarkService
                .findAll()
                .stream()
                .map(p -> objectMapper.convertValue(p, PredefMarkDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/byID")
    public PredefMarkDTO getPredefById(@RequestParam(name = "id") String id) {
        return objectMapper.convertValue(predefMarkService.findByID(id).get(), PredefMarkDTO.class);
    }

    @GetMapping(value = "/byName")
    public PredefMarkDTO getPredefByName(@RequestParam(name = "name") String name) {
        return objectMapper.convertValue(predefMarkService.findByName(name).get(), PredefMarkDTO.class);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveOrUpdatePredef(@RequestBody PredefMarkDTO predefMarkDTO) {
        predefMarkService.saveOrUpdatePredef(objectMapper.convertValue(predefMarkDTO, PredefMark.class));
        return new ResponseEntity("Template added successfully.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteID")
    public ResponseEntity<?> deletePredefById(@RequestParam(name = "id") String id) {
        predefMarkService.deletePredefByID(id);
        return new ResponseEntity("Template deleted successfully.", HttpStatus.OK);
    }
}
