package com.example.OnlineSinema.service.impl;

import com.example.OnlineSinema.domain.Genres;
import com.example.OnlineSinema.dto.genresDTO.GenresOutputDTO;
import com.example.OnlineSinema.exceptions.GenreAlreadyExistsException;
import com.example.OnlineSinema.exceptions.GenreNotFoundException;
import com.example.OnlineSinema.repository.GenreRepository;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.GenresService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GenresServiceImpl implements GenresService {

    private final GenreRepository genreRepository;
    private final FilmService filmService;
    private final ModelMapper modelMapper;

    @Autowired
    public GenresServiceImpl(GenreRepository genreRepository, ModelMapper modelMapper, FilmService filmService) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
        this.filmService = filmService;
    }

    @Override
    @Transactional
    public void save(GenresOutputDTO genresOutputDTO) {
        if (genreRepository.findByName(genresOutputDTO.getGenres()) != null) {
            throw new GenreAlreadyExistsException("Genre with name: " + genresOutputDTO.getGenres() + " already exists");
        }
        Genres genres = modelMapper.map(genresOutputDTO, Genres.class);
        genreRepository.save(genres);
    }

    @Override
    public List<GenresOutputDTO> findAll() {
        return genreRepository.findAll().stream()
                .map(genres -> modelMapper.map(genres, GenresOutputDTO.class))
                .toList();
    }

    @Override
    public GenresOutputDTO findById(int id) {
        var genre = genreRepository.findById(id);
        if (genre == null) {
            throw new GenreNotFoundException("Genre with ID: " + id + " not found");
        }
        return modelMapper.map(genre, GenresOutputDTO.class);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        var genre = genreRepository.findById(id);
        if (genre == null) {
            throw new GenreNotFoundException("Genre with ID: " + id + " not found");
        }

        genre.getFilms().forEach(film -> {
            film.getGenresList().remove(genre);
            List<String> genres = film.getGenresList().stream().map(Genres::getGenres).toList();
            filmService.update(film.getId(), film.getTitle(), genres);
        });

        genre.setFilms(new ArrayList<>());
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, String name) {
        var genre = genreRepository.findById(id);
        if (genre == null) {
            throw new GenreNotFoundException("Genre with ID: " + id + " not found");
        }

        if (genreRepository.findByName(name) != null && !genre.getGenres().equals(name)) {
            throw new GenreAlreadyExistsException("Genre with name: " + name + " already exists");
        }

        genre.setGenres(name);
        genreRepository.save(genre);
    }

    @Override
    public Page<GenresOutputDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        var genres = genreRepository.findAll(pageable);
        return genres.map(genres1 -> modelMapper.map(genres1, GenresOutputDTO.class));
    }
}