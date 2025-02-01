package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.ActorDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActorService {
    List<ActorDTO> findActorsByFilmId(int filmId);
    Page<ActorDTO> findAllPage(int page, int size);
    ActorDTO findActorById(int actorId);

    void update(ActorDTO actorDTO, int actorId);

    void create(ActorDTO actorDTO);

    void delete(int actorId);
}
