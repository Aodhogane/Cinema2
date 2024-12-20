package com.example.OnlineSinema.service;

import com.example.OnlineSinema.dto.actorsDTO.ActorsOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActorsServis {
    void save(ActorsOutputDTO actorsOutputDTO);
    List<ActorsOutputDTO> findAll();
    ActorsOutputDTO findById(int id);
    void update(int id, String surname, String name, String MiddleName);
    List<ActorsOutputDTO> findByFilmId(int id);
    Page<ActorsOutputDTO> findAll(int page, int size);
    void deleteById(int id);
    List<FilmOutputDTO> findFilmsByActorId(int actorId);
}
