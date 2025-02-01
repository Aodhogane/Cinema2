package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.DirectorDTO;
import com.example.OnlineSinema.domain.Directors;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.repository.DirectorsRepository;
import com.example.OnlineSinema.service.DirectorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DirectorServiceImpl implements DirectorService {

    private final DirectorsRepository directorsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DirectorServiceImpl(DirectorsRepository directorsRepository, ModelMapper modelMapper) {
        this.directorsRepository = directorsRepository;
        this.modelMapper = modelMapper;
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
}
