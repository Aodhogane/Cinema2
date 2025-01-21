package com.example.OnlineSinema.service.impl;

import com.example.OnlineSinema.domain.Directors;
import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.dto.directorDTO.DirectorOutputDTO;
import com.example.OnlineSinema.dto.directorDTO.DirectorsInfoDto;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.exceptions.DirectorsNotFound;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.repository.DirectorRepository;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.service.DirectorsService;
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
public class DirectorsServiceImpl implements DirectorsService {

    private final DirectorRepository directorsRepository;
    private final FilmRepository filmRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DirectorsServiceImpl(DirectorRepository directorsRepository, FilmRepository filmRepository, ModelMapper modelMapper) {
        this.directorsRepository = directorsRepository;
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void save(DirectorOutputDTO directorInputDTO) {
        Directors directors = modelMapper.map(directorInputDTO, Directors.class);
        directorsRepository.save(directors);
    }

    @Override
    public List<DirectorOutputDTO> findAll() {
        return directorsRepository.findAll().stream()
                .map(director -> modelMapper.map(director, DirectorOutputDTO.class))
                .toList();
    }

    @Override
    public List<DirectorOutputDTO> findByFilmId(int id) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            throw new FilmNotFounf("Film with ID: " + id + " not found");
        }
        List<Directors> directors = directorsRepository.findByFilmId(id);

        return directors.stream()
                .map(director -> modelMapper.map(director, DirectorOutputDTO.class))
                .toList();
    }

    @Override
    public DirectorOutputDTO findById(int id) {
        Directors directors = directorsRepository.findById(id);
        if (directors == null) {
            throw new DirectorsNotFound("Director with ID: " + id + " not found");
        }
        return modelMapper.map(directors, DirectorOutputDTO.class);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Directors directors = directorsRepository.findById(id);
        if (directors == null) {
            throw new DirectorsNotFound("Director with ID: " + id + " not found");
        }
        directorsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, String name, String surname, String middleName) {
        Directors directors = directorsRepository.findById(id);
        if (directors == null) {
            throw new DirectorsNotFound("Director with ID: " + id + " not found");
        }

        directors.setName(name);
        directors.setSurname(surname);
        directors.setMidlName(middleName);
        directorsRepository.save(directors);
    }

    @Override
    public Page<DirectorsInfoDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Directors> directorsPage = directorsRepository.findAll(pageable);

        return directorsPage.map(director -> modelMapper.map(director, DirectorsInfoDto.class));
    }

    @Override
    public List<DirectorOutputDTO> findAllWithFilms(){
        List<Directors> directors = directorsRepository.findAllWithFilms();
        return directors.stream()
                .map(director -> modelMapper.map(
                        director, DirectorOutputDTO.class))
                .toList();
    }

    @Override
    public List<FilmOutputDTO> findFilmsByDirectorId(int directorId) {
        Directors director = directorsRepository.findById(directorId);
        if (director == null) {
            throw new DirectorsNotFound("Director with ID: " + directorId + " not found");
        }

        List<Film> films = filmRepository.findFilmsByDirectorId(directorId);

        return films.stream()
                .map(film -> modelMapper.map(film, FilmOutputDTO.class))
                .toList();
    }
}