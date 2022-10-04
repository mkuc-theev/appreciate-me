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
    public List<PredefMark> findAll() {
        return predefMarkRepository.findAll();
    }

    @Override
    public Optional<PredefMark> findByName(String name) {
        return predefMarkRepository.findByName(name);
    }

    @Override
    public Optional<PredefMark> findByID(String id) {
        return predefMarkRepository.findById(id);
    }

    @Override
    public PredefMark saveOrUpdatePredef(PredefMark predefMark) {
        return predefMarkRepository.save(predefMark);
    }

    @Override
    public void deletePredefByID(String id) {
        predefMarkRepository.deleteById(id);
    }
}
