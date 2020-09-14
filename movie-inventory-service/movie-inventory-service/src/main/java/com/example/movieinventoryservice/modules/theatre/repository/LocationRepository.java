package com.example.movieinventoryservice.modules.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieinventoryservice.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
