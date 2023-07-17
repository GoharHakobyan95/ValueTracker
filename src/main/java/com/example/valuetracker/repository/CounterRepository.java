package com.example.valuetracker.repository;

import com.example.valuetracker.entity.CounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository extends JpaRepository<CounterEntity, Long> {

}
