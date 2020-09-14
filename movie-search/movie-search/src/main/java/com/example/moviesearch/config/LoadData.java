package com.example.moviesearch.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.moviesearch.model.PlayData;
import com.example.moviesearch.repository.PlayDataRepository;
import com.example.moviesearch.restApiConfig.ApiSuccessResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoadData {

	private Logger logger = LoggerFactory.getLogger(LoadData.class);

	@Autowired
	private ElasticsearchOperations operations;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PlayDataRepository playDataRepository;

	@PostConstruct
	@Transactional
	public void loadAll() {

		operations.putMapping(PlayData.class);
		System.out.println("Loading Data");

		logger.info("Entered into Loading Data services");

		ApiSuccessResponse response = restTemplate
				.exchange("http://localhost:8081/play/all", HttpMethod.GET, null, ApiSuccessResponse.class)
				.getBody();

		logger.info(response.getBody().toString());

		List<PlayData> play = objectMapper.convertValue(response.getBody(), new TypeReference<List<PlayData>>() {
		});

		playDataRepository.saveAll(play);
		
		logger.info(play.toString());

		System.out.printf("Loading Completed");

	}

}
