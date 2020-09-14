package com.example.moviesearch.config;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import com.example.moviesearch.model.PlayData;

@Component
public class SearchBuilder {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    public List<PlayData> getAll(String text) {

        QueryBuilder query = QueryBuilders.boolQuery()
                .should(
                        QueryBuilders.queryStringQuery(text)
                                .lenient(true)
                                .field("movie.name")
                                .field("movie.cast.name")
                                .field("screen.theatre.name")
                ).should(QueryBuilders.queryStringQuery("*" + text + "*")
                        .lenient(true)
                        .field("movie.name")
                        .field("movie.cast.name")
                        .field("screen.theatre.name"));

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();

        List<PlayData> userses = elasticsearchTemplate.queryForList(build, PlayData.class);

        return userses;
    }
}