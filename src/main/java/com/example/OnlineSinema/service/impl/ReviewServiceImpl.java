package com.example.OnlineSinema.service.impl;


import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Reviews;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.dto.filmDTO.FilmCardDTO;
import com.example.OnlineSinema.dto.reviewDTO.ReviewInputDTO;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.exceptions.ReviewNotFound;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.repository.ReviewsRepository;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.LinkedHashSet;


@Service
@EnableCaching
@Transactional
public class ReviewServiceImpl implements ReviewsService {

    private final UserRepository userRepository;
    private final ReviewsRepository reviewsRepository;
    private final FilmRepository filmRepository;

    @Autowired
    public ReviewServiceImpl(ReviewsRepository reviewsRepository, UserRepository userRepository, FilmRepository filmRepository) {
        this.userRepository = userRepository;
        this.reviewsRepository = reviewsRepository;
        this.filmRepository = filmRepository;
    }

    private ReviewOutputDTO createReviewOutputDto(Reviews review) {
        User user = review.getUser();
        Film film = review.getFilm();

        return new ReviewOutputDTO(
                review.getId(),
                user.getId(),
                (long) film.getId(),
                review.getEstimation(),
                review.getComment(),
                user.getUsername(),
                film.getTitle(),
                review.getDateTime()
        );
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "CLIENT_PAGE", key = "#reviewInputDTO.userId"),
            @CacheEvict(value = "FILM_PAGE", key = "#reviewInputDTO.filmId")
    })
    public void save(ReviewInputDTO reviewInputDTO) {
        User user = userRepository.findById(reviewInputDTO.getUserId());

        Film film = filmRepository.findById(reviewInputDTO.getFilmId());

        List<Reviews> reviews = reviewsRepository.findByFilmIdAndUserId(film.getId(), user.getId());

        Reviews review = new Reviews(
                user,
                film,
                reviewInputDTO.getComment(),
                reviewInputDTO.getEstimation(),
                LocalDateTime.now()
        );

        reviewsRepository.save(review);

        updateRatingFilm(film.getId());
    }

    @Override
    public ReviewOutputDTO findById(int id) {
        Reviews review = reviewsRepository.findById(id);
        return createReviewOutputDto(review);
    }

    @Override
    public void deleteById(int id) {
        reviewsRepository.deleteById(id);
    }

    @Override
    public void update(int id, ReviewOutputDTO reviewOutputDTO) {
        Reviews review = reviewsRepository.findById(id);
        review.setComment(reviewOutputDTO.getComment());
        review.setEstimation(reviewOutputDTO.getEstimation());
        reviewsRepository.save(review);
    }

    @Override
    public List<ReviewOutputDTO> findByUserId(int id) {
        List<Reviews> reviews = reviewsRepository.findByUserId(id);
        return reviews.stream().map(this::createReviewOutputDto).toList();
    }

    @Override
    public List<ReviewOutputDTO> findByFilmId(int id) {
        List<Reviews> reviews = reviewsRepository.findByFilmId(id);
        return reviews.stream().map(this::createReviewOutputDto).toList();
    }

    @Override
    public List<ReviewOutputDTO> findAll() {
        List<Reviews> reviews = reviewsRepository.findAll();
        return reviews.stream().map(this::createReviewOutputDto).toList();
    }

    @Override
    public Page<ReviewOutputDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Reviews> reviewsPage = reviewsRepository.findAll(pageable);

        List<ReviewOutputDTO> reviewOutputDTOList = reviewsPage.getContent().stream()
                .map(review -> new ReviewOutputDTO(
                        review.getId(),
                        review.getUser().getId(),
                        (long) review.getFilm().getId(),
                        review.getEstimation(),
                        review.getComment(),
                        review.getUser().getUsername(),
                        review.getFilm().getTitle(),
                        review.getDateTime()
                ))
                .toList();

        return new PageImpl<>(reviewOutputDTOList, pageable, reviewsPage.getTotalElements());
    }

    @Override
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
    public Set<FilmCardDTO> getReviewsByUserId(int id) {
        List<Reviews> reviewsList = reviewsRepository.findByUserId(id);

        return reviewsList.stream()
                .map(reviews -> {
                    Film film = reviews.getFilm();
                    return new FilmCardDTO(
                            film.getId(),
                            reviews.getEstimation(),
                            film.getGenresList(),
                            film.getTitle(),
                            reviews.getDateTime(),
                            reviews.getComment()
                    );
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}