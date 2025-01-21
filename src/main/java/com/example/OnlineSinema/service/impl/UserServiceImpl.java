package com.example.OnlineSinema.service.impl;

import com.example.OnlineSinema.controller.UserControllerImpl;
import com.example.OnlineSinema.domain.Access;
import com.example.OnlineSinema.domain.Reviews;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.dto.reviewDTO.ReviewOutputDTO;
import com.example.OnlineSinema.dto.userDTO.UserInfoDTO;
import com.example.OnlineSinema.dto.userDTO.UserOutputDTO;
import com.example.OnlineSinema.exceptions.ThisEmailAlreadyConnected;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.AccessRepository;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.ReviewsService;
import com.example.OnlineSinema.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableCaching
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReviewsService reviewsService;
    private final ModelMapper modelMapper;
    private final AccessRepository accessRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final Map<String, Integer> authTokens = new HashMap<>();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ReviewsService reviewsService,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder passwordEncoder,
                           AccessRepository accessRepository) {
        this.userRepository = userRepository;
        this.reviewsService = reviewsService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.accessRepository = accessRepository;
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
                .toList();
    }

    @Override
    @Cacheable(value = "USER_PAGE", key = "#id")
    public UserInfoDTO findById(int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFound("User not found with id: " + id);
        }

        List<String> reviews = reviewsService.findByUserId(user.getId())
                .stream()
                .map(ReviewOutputDTO::getComment)
                .toList();

        return new UserInfoDTO(user.getId(), user.getUsername(), reviews);
    }

    @Override
    @Transactional
    @CacheEvict(value = "USER_PAGE", key = "#id")
    public void update(UserOutputDTO userOutputDTO) {
        User user = userRepository.findById(userOutputDTO.getId());
        if (user == null) {
            throw new UserNotFound("User not found with id: " + userOutputDTO.getId());
        }

        user.setUsername(userOutputDTO.getName());
        user.setEmail(userOutputDTO.getEmail());
        user.setPassword(userOutputDTO.getPassword());
        userRepository.save(user);
    }

    @Override
    public UserOutputDTO findByName(String email) {
        User user = userRepository.findByName(email);
        if (user == null) {
            throw new UserNotFound("User not found with email: " + email);
        }
        return modelMapper.map(user, UserOutputDTO.class);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UserNotFound("User not found with email: " + username);
        }
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public Page<UserInfoDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> users = userRepository.findAll(pageable);

        return users.map(user -> {
            List<String> reviews = reviewsService.findByUserId(user.getId())
                    .stream()
                    .map(ReviewOutputDTO::getComment)
                    .toList();

            return new UserInfoDTO(user.getId(), user.getUsername(), reviews);
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
    public void register(String username, String email, String password, int accessId) {
        if (userRepository.existsByEmail(email)) {
            LOG.warn("Attempt to register an existing email: {}", email);
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        int defaultAccessId = 2;
        Access userAccess = accessRepository.findById(defaultAccessId);
        if (userAccess == null) {
            throw new RuntimeException("Access with id " + defaultAccessId + " not found");
        }

        user.setAccess(userAccess);

        userRepository.save(user);

        LOG.info("User successfully registered: {} with email: {}", username, email);
    }

    @Override
    public UserInfoDTO findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFound("User with username " + username + " not found"));

        List<String> reviews = Optional.ofNullable(user.getReviewsList())
                .orElse(Collections.emptyList())
                .stream()
                .map(Reviews::getComment)
                .toList();

        return new UserInfoDTO(user.getId(), user.getUsername(), reviews);
    }

    @Override
    public List<User> findUsersByAccessId(int accessId){
        return userRepository.findByAccessId(accessId);
    }
}