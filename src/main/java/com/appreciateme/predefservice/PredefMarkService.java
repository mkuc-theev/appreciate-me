package com.appreciateme.predefservice;

import java.util.List;
import java.util.Optional;

public interface PredefMarkService {

    List<PredefMarkDTO> findAll();

    Optional<PredefMarkDTO> findByName(String name);

    Optional<PredefMarkDTO> findByID(String id);

    PredefMarkDTO saveOrUpdatePredef(PredefMarkDTO predefMarkDTO);

    void deletePredefByID(String id);

}
