package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.FilmActorDTO;

public interface FilmActorService {
    void create(FilmActorDTO filmActorDTO);
    void delete(int filmId, int actorId);
}
