package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.ReviewDTO;
import com.example.OnlineSinema.domain.Client;
import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Reviews;
import com.example.OnlineSinema.repository.ClientRepository;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.repository.ReviewRepository;
import com.example.OnlineSinema.service.FilmService;
import com.example.OnlineSinema.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final FilmRepository filmRepository;
    private final ClientRepository clientRepository;
    private final FilmService filmService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ModelMapper modelMapper,
                             FilmRepository filmRepository,
                             ClientRepository clientRepository,
                             FilmService filmService) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.filmRepository = filmRepository;
        this.clientRepository = clientRepository;
        this.filmService = filmService;
    }

    @Override
    public List<ReviewDTO> findReviewByFilmId(int filmId){
        List<Reviews> reviews = reviewRepository.findReviewByFilmId(filmId);

        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Reviews reviews1 : reviews){
            ReviewDTO var = modelMapper.map(reviews1, ReviewDTO.class);
            reviewDTOS.add(var);
        }
        return reviewDTOS;
    }

    @Override
    public List<ReviewDTO> findReviewByClientId(int clientId){
        List<Reviews> reviews = reviewRepository.findReviewByClientId(clientId);

        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Reviews reviews1 : reviews){
            ReviewDTO var = modelMapper.map(reviews1, ReviewDTO.class);
            reviewDTOS.add(var);
        }
        return reviewDTOS;
    }

    @Override
    public ReviewDTO findReviewById(int reviewId){
        Reviews reviews = reviewRepository.findById(Reviews.class, reviewId);
        return modelMapper.map(reviews, ReviewDTO.class);
    }

    @Override
    @Transactional
    public void addReview(ReviewDTO reviewDTO){
        // TODO: тут надо как-то проверить на валидность reviewDTO
        Reviews review = modelMapper.map(reviewDTO, Reviews.class);
        Film film = filmRepository.findById(Film.class, reviewDTO.getFilmId());
        Client client = clientRepository.findById(Client.class, reviewDTO.getClientId());
        review.setFilm(film);
        review.setClient(client);
        review.setDateTime(LocalDateTime.now());
        filmService.updateRating(reviewDTO.getFilmId());
        reviewRepository.create(review);
    }

    @Override
    public Page<ReviewDTO> findAllPage(int page, int size){
        Page<Reviews> reviews = reviewRepository.findAllPage(Reviews.class, page, size);

        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Reviews reviews1 : reviews){
            ReviewDTO reviewDTO = modelMapper.map(reviews1, ReviewDTO.class);
            reviewDTOS.add(reviewDTO);
        }

        return new PageImpl<>(reviewDTOS, PageRequest.of(page - 1, size), reviews.getTotalElements());
    }
}
