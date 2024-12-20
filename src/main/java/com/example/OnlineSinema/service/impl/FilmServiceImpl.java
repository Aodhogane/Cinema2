package com.example.OnlineSinema.service.impl;

import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Genres;
import com.example.OnlineSinema.domain.Reviews;
import com.example.OnlineSinema.dto.actorsDTO.ActorsOutputDTO;
import com.example.OnlineSinema.dto.directorDTO.DirectorOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import com.example.OnlineSinema.dto.filmDTO.FilmSalesDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.exceptions.GenreNotFoundException;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.repository.GenreRepository;
import com.example.OnlineSinema.repository.ReviewsRepository;
import com.example.OnlineSinema.repository.TicketRepository;
import com.example.OnlineSinema.service.FilmService;
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
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private final TicketRepository ticketRepository;
    private final ReviewsRepository reviewsRepository;

    @Autowired
    public FilmServiceImpl(ModelMapper modelMapper,
                           GenreRepository genreRepository,
                           FilmRepository filmRepository,
                           TicketRepository ticketRepository,
                           ReviewsRepository reviewsRepository) {
        this.modelMapper = modelMapper;
        this.genreRepository = genreRepository;
        this.filmRepository = filmRepository;
        this.ticketRepository = ticketRepository;
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    @Transactional
    public void save(FilmOutputDTO filmOutputDTO) {
        Film filmSearch = filmRepository.findByTitle(filmOutputDTO.getTitle());
        if (filmSearch != null) {
            throw new FilmNotFounf("Film with title: " + filmOutputDTO.getTitle() + " already exists");
        }

        List<Genres> genresList = new ArrayList<>();
        for (String s : filmOutputDTO.getGenres()) {
            Genres genres = genreRepository.findByName(s);
            if (genres == null) {
                throw new GenreNotFoundException("Genre with name: " + s + " not found");
            }
            genresList.add(genres);
        }

        Film film = modelMapper.map(filmOutputDTO, Film.class);
        film.setGenres(genresList);
        filmRepository.save(film);
    }

    @Override
    public List<FilmCardDTO> findAll() {
        return filmRepository.findAll().stream()
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
        Pageable pageable = PageRequest.of(page, size);

        // Поиск фильмов по жанру
        Page<Film> films = filmRepository.findByGenre(genre, pageable);

        // Конвертация сущностей Film в DTO
        return films.map(this::convertToFilmCardDTO);
    }

    private FilmCardDTO convertToFilmCardDTO(Film film) {
        return new FilmCardDTO(
                film.getId(),
                film.getRating(),
                film.getGenres(),
                film.getTitle(),
                film.getExitDate()
        );
    }

    @Override
    public FilmOutputDTO findById(int id) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            throw new FilmNotFounf("Film with ID: " + id + " not found");
        }
        return modelMapper.map(film, FilmOutputDTO.class);
    }

    @Override
    public Page<FilmCardDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Film> filmPage = filmRepository.findAll(pageable);
        return filmPage.map(film -> modelMapper.map(film, FilmCardDTO.class));
    }

    @Override
    @Transactional
    public void update(int id, String title, List<String> genres) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            throw new FilmNotFounf("Film with ID: " + id + " not found");
        }

        film.setTitle(title);

        List<Genres> genresList = new ArrayList<>();
        for (String s : genres) {
            Genres genre = genreRepository.findByName(s);
            if (genre == null) {
                throw new GenreNotFoundException("Genre with name: " + s + " not found");
            }
            genresList.add(genre);
        }

        film.setGenres(genresList);
        filmRepository.save(film);
    }

    @Override
    @Transactional
    public void updateRatingFilm(int id) {
        Film film = filmRepository.findById(id);
        if (film == null) {
            throw new FilmNotFounf("Film with ID: " + id + " not found");
        }

        List<Reviews> reviews = reviewsRepository.findByFilmId(id);

        double sumRating = 0;
        double countRating = reviews.size();
        for (Reviews r : reviews) {
            sumRating += r.getEstimation();
        }

        double filmRating = countRating > 0 ? sumRating / countRating : 0;
        film.setRating(filmRating);
        filmRepository.save(film);
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
    @Transactional
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
    public List<FilmOutputDTO> findTopFilmsByReviewCount(boolean isTop) {
        List<Film> topFilms = filmRepository.findTopFilmsByReviewCount(isTop);
        return topFilms.stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }


    private FilmOutputDTO convertToOutputDto(Film film) {
        int reviewCount = film.getReviews().size();

        List<DirectorOutputDTO> directors = film.getDirectors().stream()
                .map(director -> new DirectorOutputDTO(
                        director.getId(),
                        director.getFilms().stream().map(Film::getTitle).collect(Collectors.toList()),
                        director.getMidlName(),
                        director.getSurname(),
                        director.getName()
                ))
                .collect(Collectors.toList());

        List<ActorsOutputDTO> actors = film.getActors().stream()
                .map(actor -> new ActorsOutputDTO(
                        actor.getId(),
                        actor.getMidlName(),
                        actor.getFilms().stream().map(Film::getTitle).collect(Collectors.toList()),
                        actor.getSurname(),
                        actor.getName()
                ))
                .collect(Collectors.toList());

        return new FilmOutputDTO(
                film.getId(),
                film.getActors().stream()
                        .map(actor -> actor.getName() + " " + actor.getSurname()) // собираем полное имя актера
                        .collect(Collectors.toList()),
                film.getGenres().stream().map(Genres::getGenres).collect(Collectors.toList()),
                film.getDirectors().stream()
                        .map(director -> director.getName() + " " + director.getSurname())
                        .collect(Collectors.toList()),
                film.getExitDate(),
                film.getTitle(),
                film.getReviews().size(),
                film.getRating()
        );
    }

    @Override
    public List<String> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(Genres::getGenres)
                .collect(Collectors.toList());
    }
}