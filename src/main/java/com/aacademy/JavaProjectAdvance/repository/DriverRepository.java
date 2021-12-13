package com.aacademy.JavaProjectAdvance.repository;

import com.aacademy.JavaProjectAdvance.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByDriverNumber(String number);
}
