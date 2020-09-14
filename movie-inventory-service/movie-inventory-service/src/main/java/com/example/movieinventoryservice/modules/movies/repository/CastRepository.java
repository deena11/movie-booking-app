package com.example.movieinventoryservice.modules.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieinventoryservice.entity.Cast;



@Repository
public interface CastRepository extends JpaRepository<Cast, Integer> {

}
