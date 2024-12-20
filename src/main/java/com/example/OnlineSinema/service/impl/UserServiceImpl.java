package com.example.OnlineSinema.service.impl;

import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.dto.userDTO.UserInfoDTO;
import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
import com.example.OnlineSinema.exceptions.ThisEmailAlreadyConnected;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.ReviewsService;
import com.example.OnlineSinema.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReviewsService reviewsService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    private final Map<String, Integer> authTokens = new HashMap<>();

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ReviewsService reviewsService,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.reviewsService = reviewsService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void save(UserOutputDTO userOutputDTO) {
        User user = userRepository.findByEmail(userOutputDTO.getEmail());
        if (user != null) {
            throw new ThisEmailAlreadyConnected("Client with email: " + userOutputDTO.getEmail() + " already exists");
        }
        userRepository.save(modelMapper.map(userOutputDTO, User.class));
    }

    @Override
    public List<UserOutputDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserOutputDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserOutputDTO findById(int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFound("User not found with id: " + id);
        }

        List<ReviewOutputDTO> reviews = reviewsService.findByUserId(user.getId())
                .stream()
                .map(review -> new ReviewOutputDTO(
                        review.getId(),
                        user.getId(),
                        review.getFilmId(),
                        review.getEstimation(),
                        review.getComment(),
                        user.getName(),
                        review.getFilmName(),
                        review.getDateTime()
                ))
                .collect(Collectors.toList());

        return new UserOutputDTO(user.getId(), user.getName(), reviews);
    }

    @Override
    @Transactional
    public void update(UserOutputDTO userOutputDTO) {
        User user = userRepository.findById(userOutputDTO.getId());
        if (user == null) {
            throw new UserNotFound("User not found with id: " + userOutputDTO.getId());
        }

        user.setName(userOutputDTO.getName());
        user.setEmail(userOutputDTO.getEmail());
        user.setPassword(userOutputDTO.getPassword());
        userRepository.save(user);
    }

    @Override
    public UserOutputDTO findByName(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new UserNotFound("User not found with name: " + name);
        }
        return modelMapper.map(user, UserOutputDTO.class);
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFound("User not found with email: " + email);
        }
        return user.getPassword().equals(password);
    }

    @Override
    public Page<UserOutputDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> users = userRepository.findAll(pageable);

        return users.map(user -> {
            List<ReviewOutputDTO> reviews = reviewsService.findByUserId(user.getId());
            return new UserOutputDTO(user.getId(), user.getName(), reviews);
        });
    }

    @Override
    @Transactional
    public void delete(int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFound("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void register(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    @Override
    public boolean authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFound("User not found with email: " + email);
        }
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public String findUserNameById(int userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        return user.getName();
    }

    @Override
    public UserInfoDTO findByUsername(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new UserNotFound("User not found with this name: " + name);
        }

        List<String> reviewComments  = reviewsService.findByUserId(user.getId())
                .stream()
                .map(ReviewOutputDTO::getComment)
                .collect(Collectors.toList());

        return new UserInfoDTO(user.getId(), user.getName(), reviewComments );
    }
}