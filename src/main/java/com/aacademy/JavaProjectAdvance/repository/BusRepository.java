package com.aacademy.JavaProjectAdvance.repository;

import com.aacademy.JavaProjectAdvance.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    Optional<Bus> findByNumber(String number);
}
