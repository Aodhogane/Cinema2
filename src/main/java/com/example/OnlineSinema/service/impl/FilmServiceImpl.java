package com.example.OnlineSinema.service.impl;

import com.example.OnlineSinema.controller.UserControllerImpl;
import com.example.OnlineSinema.domain.*;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmSalesDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.exceptions.GenreNotFoundException;
import com.example.OnlineSinema.repository.*;
import com.example.OnlineSinema.service.FilmService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableCaching
@Transactional
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private final ReviewsRepository reviewsRepository;
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository, GenreRepository genreRepository,
                           ModelMapper modelMapper,
                           ReviewsRepository reviewsRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    @CacheEvict(cacheNames = "FILM_TOP", allEntries = true)
    public void save(FilmOutputDTO filmOutputDTO) {
        Film filmSearch = filmRepository.findByTitle(filmOutputDTO.getTitle());
        if (filmSearch != null) {
            throw new FilmNotFounf("Film with title: " + filmOutputDTO.getTitle() + " already exists");
        }

        Set<Genres> genresList = new LinkedHashSet<>();
        for (String s : filmOutputDTO.getGenres()) {
            Genres genre = genreRepository.findByName(s);
            if (genre == null) {
                throw new GenreNotFoundException("Genre with name: " + s + " not found");
            }
            genresList.add(genre);
        }

        Film film = modelMapper.map(filmOutputDTO, Film.class);
        film.setGenresList(genresList);
        filmRepository.save(film);
    }

    @Override
    public List<FilmCardDTO> findAll() {
        return filmRepository.findAll().stream()
                .map(film -> modelMapper.map(film, FilmCardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmCardDTO> findByNameContaining(String title) {
        return filmRepository.findByTitleContaining(title).stream()
                .map(film -> modelMapper.map(film, FilmCardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FilmCardDTO> findByGenres(List<String> genres) {
        List<Genres> genresList = new ArrayList<>();
        for (String s : genres) {
            Genres genre = genreRepository.findByName(s);
            if (genre == null) {
                throw new GenreNotFoundException("Genre with name: " + s + " not found");
            }
            genresList.add(genre);
        }
        return filmRepository.findByGenres(genresList).stream()
                .map(film -> modelMapper.map(film, FilmCardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FilmCardDTO> findByGenre(String genre, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Genres genreObj = genreRepository.findByName(genre);
        if (genreObj == null) {
            throw new GenreNotFoundException("Genre with name: " + genre + " not found");
        }

        Page<Film> films = filmRepository.findByGenres(Collections.singletonList(genreObj), pageable);
        return films.map(this::convertToFilmCardDTO);
    }

    private FilmCardDTO convertToFilmCardDTO(Film film) {
        return new FilmCardDTO(
                film.getId(),
                film.getRating(),
                new LinkedHashSet<>(film.getGenresList()),
                film.getTitle(),
                film.getExitDate(),
                film.getTitle()
        );
    }

    @Override
    @Cacheable(value = "FILM_PAGE")
    public FilmOutputDTO findById(int id) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            LOG.warn("Film with ID: {} not found", id);
            throw new FilmNotFounf("Film with ID: " + id + " not found");
        }
        LOG.warn("Film found: {}", film);
        return modelMapper.map(film, FilmOutputDTO.class);
    }

    @Override
    public Page<FilmCardDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Film> filmPage = filmRepository.findAll(pageable);
        return filmPage.map(film -> modelMapper.map(film, FilmCardDTO.class));
    }

    @Override
    @Transactional
    @CacheEvict(value = "FILM_PAGE")
    public void update(int id, String title, List<String> genres) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            throw new FilmNotFounf("Film with ID: " + id + " not found");
        }

        film.setTitle(title);

        Set<Genres> genresList = new LinkedHashSet<>();
        for (String s : genres) {
            Genres genre = genreRepository.findByName(s);
            if (genre == null) {
                throw new GenreNotFoundException("Genre with name: " + s + " not found");
            }
            genresList.add(genre);
        }

        film.setGenresList(genresList);
        filmRepository.save(film);
    }

    @Override
    @Transactional
    public void updateRatingFilm(int id) {
       List<Reviews> reviews = reviewsRepository.findByFilmId(id);


       double averageRating  = reviews.stream()
               .mapToInt(Reviews::getEstimation)
               .average()
               .orElse(0);

       Film film = filmRepository.findById(id);
       if(film != null){
           film.setRating(averageRating);
           filmRepository.save(film);
       }
    }

    @Override
    public FilmOutputDTO findByTitle(String title) {
        Film film = filmRepository.findByTitle(title);
        if (film == null) {
            throw new FilmNotFounf("Film with title: " + title + " not found");
        }
        return modelMapper.map(film, FilmOutputDTO.class);
    }

    @Override
    public Page<FilmCardDTO> findByNameContainingAndByGenres(String filmPart, List<String> genres, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Genres> genresList = genres.stream()
                .map(genreName -> {
                    Genres genre = genreRepository.findByName(genreName);
                    if (genre == null) {
                        throw new GenreNotFoundException("Genre with name: " + genreName + " not found");
                    }
                    return genre;
                })
                .collect(Collectors.toList());

        Page<Film> filmPage = filmRepository.findByTitleContainingAndGenres(filmPart, genresList, pageable);

        return filmPage.map(film -> modelMapper.map(film, FilmCardDTO.class));
    }

    @Override
    @Transactional
    @CacheEvict(value = "FILM_PAGE")
    public void deleteById(int id) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            throw new FilmNotFounf("Film with ID: " + id + " not found");
        }
        filmRepository.deleteById(id);
    }

    @Override
    public List<FilmSalesDTO> getTop5FilmsBySales() {
        List<Film> topFilms = filmRepository.findTop5FilmsBySales();
        return topFilms.stream()
                .map(film -> new FilmSalesDTO(film.getTitle(), film.getTicketsList().size()))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("FILM_TOP")
    public List<String> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(Genres::getGenres)
                .collect(Collectors.toList());
    }

    @Override
    public Film findFilmWithDetails(int filmId) {
        Film film = filmRepository.findFilmWithDetails(filmId);
        if (film == null) {
            throw new FilmNotFounf("Film with ID: " + filmId + " not found");
        }
        return film;
    }

    @Override
    @Transactional
    public Page<FilmCardDTO> searchByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Film> films = filmRepository.findByTitleContainingIgnoreCase(title, pageable);
        return films.map(film -> modelMapper.map(film, FilmCardDTO.class));
    }
}