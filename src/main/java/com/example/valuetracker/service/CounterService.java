package com.example.valuetracker.service;


import com.example.valuetracker.entity.CounterEntity;
import com.example.valuetracker.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CounterService {

    private final CounterRepository counterRepository;

    @Autowired
    public CounterService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public CounterEntity getCounterEntity() {
        return counterRepository.findById(1L).orElse(new CounterEntity());
    }

    public CounterEntity incrementAndSaveCounter(CounterEntity counterEntity) {
        int currentValue = counterEntity.getCounterValue();
        counterEntity.setCounterValue(++currentValue);
        return counterRepository.save(counterEntity);
    }
}

