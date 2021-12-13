package com.aacademy.JavaProjectAdvance.repository;

import com.aacademy.JavaProjectAdvance.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    Optional<Station> findByName(String name);

}
