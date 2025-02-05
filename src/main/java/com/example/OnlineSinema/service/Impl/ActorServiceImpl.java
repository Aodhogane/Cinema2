package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.ActorDTO;
import com.example.OnlineSinema.DTO.inputDTO.ActorInputDTO;
import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.enums.UserRoles;
import com.example.OnlineSinema.exceptions.ActorsNotFound;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.repository.ActorRepository;
import com.example.OnlineSinema.repository.FilmActorRepository;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.ActorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ModelMapper modelMapper;
    private final FilmActorRepository filmActorRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final FilmActorRepository actorFilmRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository, ModelMapper modelMapper,
                            FilmActorRepository filmActorRepository,
                            FilmRepository filmRepository, UserRepository userRepository, FilmActorRepository actorFilmRepository) {
        this.actorRepository = actorRepository;
        this.modelMapper = modelMapper;
        this.filmActorRepository = filmActorRepository;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.actorFilmRepository = actorFilmRepository;
    }

    @Override
    public List<ActorDTO> findActorsByFilmId(int filmId){
        List<Actors> actors = actorRepository.findActorsByFilmId(filmId);

        List<ActorDTO> actorDTOS = new ArrayList<>();
        for(Actors actors1 : actors){
            ActorDTO var = modelMapper.map(actors1, ActorDTO.class);
            actorDTOS.add(var);
        }

        return actorDTOS;
    }

    @Override
    public Page<ActorDTO> findAllPage(int page, int size){
        Page<Actors> actors = actorRepository.findAllPage(Actors.class, page, size);

        List<ActorDTO> actorDTOS = new ArrayList<>();
        for (Actors actors1 : actors){
           ActorDTO actorDTO = modelMapper.map(actors1, ActorDTO.class);
           actorDTOS.add(actorDTO);
        }

        return new PageImpl<>(actorDTOS, PageRequest.of(page - 1, size), actors.getTotalElements());
    }

    @Override
    public ActorDTO findActorById(int actorId){
        Actors actors = actorRepository.findById(Actors.class, actorId);
        if (actors == null){
            throw new FilmNotFounf();
        }

        return  modelMapper.map(actors, ActorDTO.class);
    }

    @Override
    public void update(ActorDTO actorDTO, int actorId){
        Actors actorsOld = actorRepository.findById(Actors.class, actorId);
        Actors actors = modelMapper.map(actorDTO, Actors.class);

        actors.setFilmActors(actorsOld.getFilmActors());
        actors.setUser(actorsOld.getUser());
        actors.setId(actorId);
        actorRepository.update(actors);
    }

    @Override
    @Transactional
    public void create(ActorInputDTO actorInputDTO){
        User user = new User(actorInputDTO.getEmail(), actorInputDTO.getPassword(), UserRoles.ACTOR);
        Actors actors = modelMapper.map(actorInputDTO, Actors.class);

        userRepository.create(user);
        actors.setUser(user);
        actorRepository.create(actors);
    }

    @Override
    public void delete(int actorId){
        Actors actor = actorRepository.findById(Actors.class, actorId);
        actorRepository.delete(actor);
    }
}
