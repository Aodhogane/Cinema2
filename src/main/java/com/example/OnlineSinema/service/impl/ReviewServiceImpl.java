package com.example.OnlineSinema.service.impl;


import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Reviews;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.exceptions.ReviewNotFound;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.repository.ReviewsRepository;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
        return new ReviewOutputDTO(
                review.getId(),
                review.getUser().getId(),
                Long.valueOf(review.getFilm().getId()),
                review.getEstimation(),
                review.getComment(),
                review.getUser().getName(),
                review.getFilm().getTitle(),
                review.getDateTime()
        );
    }

    @Override
    @Transactional
    public void save(ReviewOutputDTO reviewOutputDTO) {
        User user = userRepository.findById(reviewOutputDTO.getUserId());
        if (user == null) {
            throw new UserNotFound("User with id: " + reviewOutputDTO.getUserId() + " not found");
        }

        Film film = filmRepository.findById(reviewOutputDTO.getFilmId().intValue());
        if (film == null) {
            throw new FilmNotFounf("Film with id: " + reviewOutputDTO.getFilmId() + " not found");
        }

        List<Reviews> reviews = reviewsRepository.findByFilmIdAndUserId(film.getId(), user.getId());
        if (!reviews.isEmpty()) {
            throw new ReviewNotFound("User with id: " + user.getId() + " already has a review for film with id: " + film.getId());
        }

        Reviews review = new Reviews(user, film, reviewOutputDTO.getComment(), reviewOutputDTO.getEstimation(), LocalDateTime.now());
        reviewsRepository.save(review);
    }

    @Override
    public ReviewOutputDTO findById(int id) {
        Reviews review = reviewsRepository.findById(id);
        if (review == null) {
            throw new ReviewNotFound("Review with id: " + id + " not found");
        }
        return createReviewOutputDto(review);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Reviews review = reviewsRepository.findById(id);
        if (review == null) {
            throw new ReviewNotFound("Review with id: " + id + " not found");
        }
        reviewsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, ReviewOutputDTO reviewOutputDTO) {
        Reviews review = reviewsRepository.findById(id);
        if (review == null) {
            throw new ReviewNotFound("Review with id: " + id + " not found");
        }

        review.setComment(reviewOutputDTO.getComment());
        review.setEstimation(reviewOutputDTO.getEstimation());
        reviewsRepository.save(review);
    }

    @Override
    public List<ReviewOutputDTO> findByUserId(int id) {
        List<Reviews> reviews = reviewsRepository.findByUserId(id);
        return reviews.stream().map(this::createReviewOutputDto).collect(Collectors.toList());
    }

    @Override
    public List<ReviewOutputDTO> findByFilmId(int id) {
        List<Reviews> reviews = reviewsRepository.findByFilmId(id);
        return reviews.stream().map(this::createReviewOutputDto).collect(Collectors.toList());
    }

    @Override
    public List<ReviewOutputDTO> getLastReviewsByUserId(int id, int count) {
        List<Reviews> reviews = reviewsRepository.getLastReviewsByUserId(id, count);
        return reviews.stream()
                .sorted((r1, r2) -> r2.getDateTime().compareTo(r1.getDateTime()))
                .limit(count)
                .map(this::createReviewOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReviewOutputDTO> getLastReviewsByFilmId(int id, int reviewPage, int reviewSize) {
        Pageable pageable = PageRequest.of(reviewPage, reviewSize);
        Page<Reviews> reviews = reviewsRepository.getLatestReviewsByFilmId(id, pageable);
        return reviews.map(this::createReviewOutputDto);
    }

    @Override
    public Page<ReviewOutputDTO> getReviewsByUserId(int id, int reviewPage, int reviewSize) {
        Pageable pageable = PageRequest.of(reviewPage, reviewSize);
        List<Reviews> reviewsList = reviewsRepository.findByUserId(id);

        int start = Math.min((int) pageable.getOffset(), reviewsList.size());
        int end = Math.min((start + pageable.getPageSize()), reviewsList.size());

        Page<Reviews> reviewsPage = new PageImpl<>(reviewsList.subList(start, end), pageable, reviewsList.size());
        return reviewsPage.map(this::createReviewOutputDto);
    }

    @Override
    public List<ReviewOutputDTO> findByRating(int filmId, float rating) {
        List<Reviews> reviews = reviewsRepository.findByFilmIdAndRating(filmId, rating);
        return reviews.stream().map(this::createReviewOutputDto).collect(Collectors.toList());
    }

    @Override
    public ReviewOutputDTO findByUserIdFilmId(int userId, int filmId) {
        List<Reviews> reviews = reviewsRepository.findByFilmIdAndUserId(filmId, userId);
        if (reviews.isEmpty()) {
            throw new ReviewNotFound("Review for user with id: " + userId + " and film with id: " + filmId + " not found");
        }
        return createReviewOutputDto(reviews.get(0));
    }

    @Override
    public List<ReviewOutputDTO> findAll() {
        List<Reviews> reviews = reviewsRepository.findAll();
        return reviews.stream().map(this::createReviewOutputDto).collect(Collectors.toList());
    }
}