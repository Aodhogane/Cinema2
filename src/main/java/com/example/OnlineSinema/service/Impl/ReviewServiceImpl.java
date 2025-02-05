package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.ReviewDTO;
import com.example.OnlineSinema.DTO.inputDTO.ReviewInputDTO;
import com.example.OnlineSinema.domain.*;
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
    public Page<ReviewDTO> findAllPage(int page, int size){
        Page<Reviews> reviews = reviewRepository.findAllPage(Reviews.class, page, size);

        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Reviews reviews1 : reviews){
            ReviewDTO reviewDTO = modelMapper.map(reviews1, ReviewDTO.class);
            reviewDTOS.add(reviewDTO);
        }

        return new PageImpl<>(reviewDTOS, PageRequest.of(page - 1, size), reviews.getTotalElements());
    }

    @Override
    public void update(ReviewDTO reviewDTO, int reviewId){

        Reviews reviewOld = reviewRepository.findById(Reviews.class, reviewId);
        Reviews reviews = modelMapper.map(reviewDTO, Reviews.class);

        reviews.setClient(reviewOld.getClient());
        reviews.setFilm(reviewOld.getFilm());

        reviews.setId(reviewId);
        reviewRepository.update(reviews);
    }

    @Override
    @Transactional
    public void create(ReviewInputDTO reviewInputDTO) {
        Reviews review = new Reviews(reviewInputDTO.getComment(), reviewInputDTO.getEstimation(), LocalDateTime.now());
        Film film = filmRepository.findById(Film.class, reviewInputDTO.getFilmId());
        Client client = clientRepository.findById(Client.class, reviewInputDTO.getClientId());
        review.setFilm(film);
        review.setClient(client);
        reviewRepository.create(review);
        filmService.updateRating(reviewInputDTO.getFilmId());
    }

    @Override
    public void delete(int reviewId){
        Reviews reviews = reviewRepository.findById(Reviews.class, reviewId);
        reviewRepository.delete(reviews);
    }
}
