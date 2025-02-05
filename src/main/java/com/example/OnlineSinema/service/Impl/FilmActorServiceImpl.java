package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.FilmActorDTO;
import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.FilmActor;
import com.example.OnlineSinema.repository.ActorRepository;
import com.example.OnlineSinema.repository.FilmActorRepository;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.service.FilmActorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmActorServiceImpl implements FilmActorService {

    private final FilmActorRepository filmActorRepository;
    private final FilmRepository filmRepository;
    private final ActorRepository actorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FilmActorServiceImpl(FilmActorRepository filmActorRepository, FilmRepository filmRepository, ActorRepository actorRepository, ModelMapper modelMapper) {
        this.filmActorRepository = filmActorRepository;
        this.filmRepository = filmRepository;
        this.actorRepository = actorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(FilmActorDTO filmActorDTO){
        FilmActor filmActor = modelMapper.map(filmActorDTO, FilmActor.class);
        Film film = filmRepository.findById(Film.class, filmActorDTO.getFilmId());
        Actors actors = actorRepository.findById(Actors.class, filmActorDTO.getActorId());

        filmActor.setFilm(film);
        filmActor.setActors(actors);
        filmActorRepository.create(filmActor);
    }

    @Override
    public void delete(int filmId, int actorId) {
        FilmActor filmActor = filmActorRepository.findFilmActorByFilmIdAndActorId(filmId, actorId);
        filmActorRepository.delete(filmActor);
    }
}
