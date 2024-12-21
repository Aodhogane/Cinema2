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
import java.util.Collections;
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
            Genres genre = genreRepository.findByName(s);
            if (genre == null) {
                throw new GenreNotFoundException("Genre with name: " + s + " not found");
            }
            genresList.add(genre);
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
        Pageable pageable = PageRequest.of(page, size);

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
        List<Film> topFilms = filmRepository.findByReviewCount();
        return topFilms.stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }

    private FilmOutputDTO convertToOutputDto(Film film) {
        int ticketCount = film.getTicketsList().size();

        List<String> directors = film.getDirectors().stream()
                .map(director -> String.format("%s %s %s", director.getName(), director.getSurname(), director.getMidlName()))
                .collect(Collectors.toList());

        List<String> actors = film.getActors().stream()
                .map(actor -> String.format("%s %s %s", actor.getName(), actor.getSurname(), actor.getMidlName()))
                .collect(Collectors.toList());

        List<String> genres = film.getGenres().stream()
                .map(Genres::getGenres)
                .collect(Collectors.toList());

        return new FilmOutputDTO(
                film.getId(),
                actors,
                genres,
                directors,
                film.getExitDate(),
                film.getTitle(),
                ticketCount,
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
