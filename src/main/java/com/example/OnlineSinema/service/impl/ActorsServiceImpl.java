package com.example.OnlineSinema.service.impl;

import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.dto.actorsDTO.ActorsOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.exceptions.ActorsNotFound;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.repository.ActorRepository;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.service.ActorsServis;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActorsServiceImpl implements ActorsServis {

    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ActorsServiceImpl(ActorRepository actorRepository, FilmRepository filmRepository, ModelMapper modelMapper) {
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void save(ActorsOutputDTO actorsInputDto) {
        Actors actors = modelMapper.map(actorsInputDto, Actors.class);
        actorRepository.save(actors);
    }

    @Override
    public List<ActorsOutputDTO> findAll() {
        return actorRepository.findAll().stream()
                .map(actors -> modelMapper.map(actors, ActorsOutputDTO.class))
                .toList();
    }

    @Override
    public ActorsOutputDTO findById(int id) {
        Actors actors = actorRepository.findById(id);
        if (actors == null) {
            throw new ActorsNotFound("Actors with ID: " + id + " not found");
        }
        return modelMapper.map(actors, ActorsOutputDTO.class);
    }

    @Override
    @Transactional
    public void update(int id, String name, String surname, String middleName) {
        Actors actors = actorRepository.findById(id);
        if (actors == null) {
            throw new ActorsNotFound("Actors with ID: " + id + " not found");
        }

        actors.setName(name);
        actors.setSurname(surname);
        actors.setMidlName(middleName);
        actorRepository.save(actors);
    }

    @Override
    public List<ActorsOutputDTO> findByFilmId(int id) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            throw new FilmNotFounf("Film with ID: " + id + " not found");
        }
        List<Actors> actors = actorRepository.findByFilmId(id);

        return actors.stream()
                .map(actors1 -> modelMapper.map(actors1, ActorsOutputDTO.class))
                .toList();
    }

    @Override
    public Page<ActorsOutputDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Actors> actorsPage = actorRepository.findAll(pageable);

        return actorsPage.map(actors -> modelMapper.map(actors, ActorsOutputDTO.class));
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Actors actors = actorRepository.findById(id);
        if (actors == null) {
            throw new ActorsNotFound("Actors with ID: " + id + " not found");
        }
        actorRepository.deleteById(id);
    }

    @Override
    public List<FilmOutputDTO> findFilmsByActorId(int actorId) {
        Actors actor = actorRepository.findById(actorId);
        if (actor == null) {
            throw new ActorsNotFound("Actor with ID: " + actorId + " not found");
        }

        List<Film> films = filmRepository.findFilmsByActorId(actorId);

        return films.stream()
                .map(film -> modelMapper.map(film, FilmOutputDTO.class))
                .toList();
    }
}