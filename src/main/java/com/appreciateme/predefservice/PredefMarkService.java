package com.appreciateme.predefservice;

import java.util.List;
import java.util.Optional;

public interface PredefMarkService {

    List<PredefMark> findAll();

    Optional<PredefMark> findByName(String name);

    Optional<PredefMark> findByID(String id);

    PredefMark saveOrUpdatePredef(PredefMark predefMark);

    void deletePredefByID(String id);

}
