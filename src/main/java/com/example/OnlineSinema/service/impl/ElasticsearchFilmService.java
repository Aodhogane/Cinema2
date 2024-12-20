package com.example.OnlineSinema.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticsearchFilmService {

    private final ElasticsearchClient elasticsearchClient;

    public ElasticsearchFilmService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public Page<FilmCardDTO> searchFilms(String query, int page, int size) throws IOException {
        var searchResponse = elasticsearchClient.search(s -> s
                        .index("films")
                        .query(q -> q
                                .multiMatch(m -> m
                                        .fields("name", "description", "actors", "directors")
                                        .query(query)
                                )
                        )
                        .from(page * size)
                        .size(size),
                FilmCardDTO.class
        );

        List<FilmCardDTO> results = searchResponse.hits().hits().stream()
                .map(hit -> hit.source())
                .collect(Collectors.toList());

        return new PageImpl<>(results, PageRequest.of(page, size), searchResponse.hits().total().value());
    }
}
