package com.example.OnlineSinema.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import com.example.OnlineSinema.controller.MainPageController;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ElasticsearchFilmService {

    private final ElasticsearchClient elasticsearchClient;
    private static final Logger LOG = LogManager.getLogger(MainPageController.class);

    public ElasticsearchFilmService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public Page<FilmCardDTO> searchFilms(String query, int page, int size) throws IOException {
        LOG.info("Movie search: query='{}', page={}, size={}", query, page, size);
        try {
            var searchResponse = elasticsearchClient.search(s -> s
                            .index("film")
                            .query(q -> q
                                    .match(m -> m
                                            .field("title")
                                            .query(query)
                                            .fuzziness("AUTO")
                                    )
                            )
                            .from(page * size)
                            .size(size),
                    FilmCardDTO.class
            );

            List<FilmCardDTO> results = Optional.ofNullable(searchResponse.hits().hits())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(hit -> hit.source())
                    .collect(Collectors.toList());

            LOG.info("Find {} films", results.size());
            return new PageImpl<>(results, PageRequest.of(page, size), searchResponse.hits().total().value());
        } catch (ElasticsearchException e) {
            if (e.getMessage().contains("index_not_found_exception")) {
                LOG.error("Error: {}", e.getMessage(), e);
            }
            throw e;
        }
    }
}
