package com.appreciateme.predefservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredefMarkServiceImpl implements PredefMarkService {
    @Autowired
    private PredefMarkRepository predefMarkRepository;

    @Override
    public List<PredefMarkDTO> findAll() {
        return predefMarkRepository.findAll();
    }

    @Override
    public Optional<PredefMarkDTO> findByName(String name) {
        return predefMarkRepository.findByName(name);
    }

    @Override
    public Optional<PredefMarkDTO> findByID(String id) {
        return predefMarkRepository.findById(id);
    }

    @Override
    public PredefMarkDTO saveOrUpdatePredef(PredefMarkDTO predefMarkDTO) {
        return predefMarkRepository.save(predefMarkDTO);
    }

    @Override
    public void deletePredefByID(String id) {
        predefMarkRepository.deleteById(id);
    }
}
