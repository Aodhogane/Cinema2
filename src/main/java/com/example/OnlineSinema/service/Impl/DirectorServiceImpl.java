package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.DirectorDTO;
import com.example.OnlineSinema.DTO.inputDTO.DirectorInputDTO;
import com.example.OnlineSinema.domain.*;
import com.example.OnlineSinema.enums.UserRoles;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.repository.DirectorsRepository;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.DirectorService;
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
public class DirectorServiceImpl implements DirectorService {

    private final DirectorsRepository directorsRepository;
    private final ModelMapper modelMapper;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Autowired
    public DirectorServiceImpl(DirectorsRepository directorsRepository,
                               ModelMapper modelMapper, FilmRepository filmRepository, UserRepository userRepository) {
        this.directorsRepository = directorsRepository;
        this.modelMapper = modelMapper;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DirectorDTO findById(int directorId){
        Directors director = directorsRepository.findById(Directors.class, directorId);
        if (director == null){
            throw new FilmNotFounf();
        }
        return modelMapper.map(director, DirectorDTO.class);
    }

    @Override
    public Page<DirectorDTO> findAllPage(int page, int size){
       Page<Directors> directors = directorsRepository.findAllPage(Directors.class, page, size);

        List<DirectorDTO> directorDTOS = new ArrayList<>();
        for (Directors directors1 : directors){
            DirectorDTO directorDTO = modelMapper.map(directors1, DirectorDTO.class);
            directorDTOS.add(directorDTO);
        }

        return new PageImpl<>(directorDTOS, PageRequest.of(page - 1, size), directors.getTotalElements());
    }

    @Override
    public void update(DirectorDTO directorDTO, int directorId){
        Directors directorOld = directorsRepository.findById(Directors.class, directorId);
        Directors directors = modelMapper.map(directorDTO, Directors.class);


        directors.setUser(directorOld.getUser());
        directors.setId(directorId);
        directorsRepository.update(directors);
    }

    @Override
    @Transactional
    public void create(DirectorInputDTO directorInputDTO){
        User user = new User(directorInputDTO.getEmail(), directorInputDTO.getPassword(), UserRoles.DIRECTOR);
        Directors directors = modelMapper.map(directorInputDTO, Directors.class);

        userRepository.create(user);
        directors.setUser(user);
        directorsRepository.create(directors);
    }


    @Override
    public void delete(int directorId){
        Directors directors = directorsRepository.findById(Directors.class, directorId);
        directorsRepository.delete(directors);
    }
}
