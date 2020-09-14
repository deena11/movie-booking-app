package com.example.moviesearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.moviesearch.model.PlayData;

@Repository
public interface PlayDataRepository extends ElasticsearchRepository<PlayData, Integer> {

}
