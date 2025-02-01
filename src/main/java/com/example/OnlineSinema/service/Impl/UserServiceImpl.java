package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.UserDTO;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
