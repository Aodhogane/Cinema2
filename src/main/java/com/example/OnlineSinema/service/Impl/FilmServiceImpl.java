package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.FilmDTO;
import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Reviews;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.repository.ReviewRepository;
import com.example.OnlineSinema.service.FilmService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository, ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.filmRepository = filmRepository;
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<FilmDTO> findAllPage(int page, int size){
        Page<Film> filmPageMain = filmRepository.findAllPage(Film.class, page, size);

        List<FilmDTO> filmDTOS = new ArrayList<>();
        for(Film film : filmPageMain){
            FilmDTO var = modelMapper.map(film, FilmDTO.class);
            filmDTOS.add(var);
        }
        return new PageImpl<>(filmDTOS, PageRequest.of(page - 1, size), filmPageMain.getTotalElements());
    }

    @Override
    public Page<FilmDTO> findFilmByTitle(String title, int page, int size){
        Page<Film> filmPage = filmRepository.findFilmByTitle(title, page, size);

        List<FilmDTO> filmDTOS = new ArrayList<>();
        for (Film film : filmPage){
            FilmDTO var = modelMapper.map(film, FilmDTO.class);
            filmDTOS.add(var);
        }

        return new PageImpl<>(filmDTOS, PageRequest.of(page -1, size),filmPage.getTotalElements());
    }

    @Override
    public Page<FilmDTO> filmFilmByGenre(String genre, int page, int size){
        Page<Film> filmPage = filmRepository.findFilmByGenres(genre, page, size);

        List<FilmDTO> filmDTOS = new ArrayList<>();
        for (Film film : filmPage){
            FilmDTO var = modelMapper.map(film, FilmDTO.class);
            filmDTOS.add(var);
        }

        return new PageImpl<>(filmDTOS, PageRequest.of(page -1, size), filmPage.getTotalElements());
    }

    @Override
    public Page<FilmDTO> chuzSort(String title, String genre, int page, int size){
        Page<FilmDTO> filmPage;

        if (title != null){
            filmPage = findFilmByTitle(title, page, size);
        } else if(genre != null){
            filmPage = filmFilmByGenre(genre, page, size);
        } else {
            filmPage = findAllPage(page, size);
        }

        return filmPage;
    }

    @Override
    public FilmDTO findFilmById(int filmId){
        Film film = filmRepository.findById(Film.class, filmId);
        if (film == null){
            throw new FilmNotFounf();
        }
        return modelMapper.map(film, FilmDTO.class);
    }

    @Override
    public void updateRating(int filmId){
        List<Reviews> review = reviewRepository.findReviewByFilmId(filmId);

        double sum = 0, sred = 0;

        for (Reviews review1 : review){
            sum = review1.getEstimation() + sum;
        }
        sred = Math.round((sum/review.size()) * 10.0) / 10.0;

        Film film = filmRepository.findById(Film.class, filmId);
        film.setRating(sred);

        filmRepository.update(film);
    }

    @Override
    public void update(FilmDTO filmDTO, int filmId){
        Film film = modelMapper.map(filmDTO, Film.class);
        Film filmOld = filmRepository.findById(Film.class, filmId);
        film.setDirectors(filmOld.getDirectors());
        film.setId(filmId);
        filmRepository.update(film);
    }

    @Override
    public List<FilmDTO> findFilmsByActorsId(int actorsId){
        List<Film> films = filmRepository.findFilmByActorsId(actorsId);

        if (films == null){
            throw new FilmNotFounf();
        }

        List<FilmDTO> filmDTOS = new ArrayList<>();
        for (Film film : films){
            FilmDTO var = modelMapper.map(film, FilmDTO.class);
            filmDTOS.add(var);
        }

        return filmDTOS;
    }

    @Override
    public List<FilmDTO> findFilmsByDirectorsId(int directorsId){
        List<Film> films = filmRepository.findFilmsByDirectorsId(directorsId);

        if (films == null){
            throw new FilmNotFounf();
        }

        List<FilmDTO> filmDTOS = new ArrayList<>();
        for (Film film : films){
            FilmDTO var = modelMapper.map(film, FilmDTO.class);
            filmDTOS.add(var);
        }

        return filmDTOS;
    }
}
