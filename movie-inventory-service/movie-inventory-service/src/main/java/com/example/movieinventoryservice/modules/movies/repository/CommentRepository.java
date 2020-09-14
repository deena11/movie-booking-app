package com.example.movieinventoryservice.modules.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieinventoryservice.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
