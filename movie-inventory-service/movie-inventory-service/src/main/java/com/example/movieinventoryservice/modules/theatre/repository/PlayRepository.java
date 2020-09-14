package com.example.movieinventoryservice.modules.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieinventoryservice.entity.Play;

@Repository
public interface PlayRepository extends JpaRepository<Play, Integer> {

}
