package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.inputDTO.ActorInputDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActorService {
    List<ActorDTO> findActorsByFilmId(int filmId);
    Page<ActorDTO> findAllPage(int page, int size);
    ActorDTO findActorById(int actorId);
    void update(ActorDTO actorDTO, int actorId);
    void create(ActorInputDTO actorInputDTO);
    void delete(int actorId);
}
