package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.UserDTO;
import com.example.OnlineSinema.domain.*;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO findById(int userId){
        User user = userRepository.findById(User.class, userId);
        if (user == null){
            throw new UserNotFound();
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void update(UserDTO userDTO, int userId){
        User user = modelMapper.map(userDTO, User.class);

        user.setId(userId);

        userRepository.update(user);
    }

    @Override
    public void create(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        userRepository.create(user);
    }

    @Override
    public void delete(int userId){
        User user = userRepository.findById(User.class, userId);
        userRepository.delete(user);
    }
}
