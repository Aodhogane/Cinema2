package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.BaseUserDTO;
import com.example.OnlineSinema.DTO.UserRegistrationDTO;
import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.domain.Client;
import com.example.OnlineSinema.domain.Directors;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.enums.UserRoles;
import com.example.OnlineSinema.exceptions.PassworNotConfirm;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.ActorRepository;
import com.example.OnlineSinema.repository.ClientRepository;
import com.example.OnlineSinema.repository.DirectorsRepository;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final DirectorsRepository directorsRepository;
    private final ActorRepository actorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(ModelMapper modelMapper, UserRepository userRepository,
                           ClientRepository clientRepository, DirectorsRepository directorsRepository,
                           ActorRepository actorRepository, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.directorsRepository = directorsRepository;
        this.actorRepository = actorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(UserRegistrationDTO regDTO){

        if (!regDTO.getPassword().equals(regDTO.getConfirmPassword())){
            throw new PassworNotConfirm("Пароли не совпали!");
        }

        User userEmail = userRepository.findUserByEmail(regDTO.getEmail());

        if (userEmail != null){
            throw new UserNotFound();
        }

        UserRoles role = UserRoles.of(regDTO.getRole());
        User user = new User(regDTO.getEmail(), passwordEncoder.encode(regDTO.getPassword()), role);

        userRepository.create(user);

        if (role == UserRoles.CLIENT){
            Client client = modelMapper.map(regDTO, Client.class);
            client.setUser(user);
            clientRepository.create(client);
        } else if (role == UserRoles.ACTOR){
            Actors actors = modelMapper.map(regDTO, Actors.class);
            actors.setUser(user);
            actorRepository.create(actors);
        } else {
            Directors directors = modelMapper.map(regDTO, Directors.class);
            directors.setUser(user);
            directorsRepository.create(directors);
        }
    }

    @Override
    public BaseUserDTO getUser(String email) {
        User user = userRepository.findUserByEmail(email);

        BaseUserDTO baseUserDTO = null;

        if(user.getUserRoles() == UserRoles.CLIENT){
            Client client = clientRepository.findClientByUserId(user.getId());
            baseUserDTO = modelMapper.map(client, BaseUserDTO.class);
        } else if (user.getUserRoles() == UserRoles.ACTOR){
            Actors actors = actorRepository.findActorsByUserId(user.getId());
            baseUserDTO = modelMapper.map(actors, BaseUserDTO.class);
        } else if (user.getUserRoles() == UserRoles.DIRECTOR || user.getUserRoles() == UserRoles.ADMIN) {
            Directors directors = directorsRepository.findDirectorsByUserId(user.getId());
            baseUserDTO = modelMapper.map(directors, BaseUserDTO.class);
        }
        return baseUserDTO;
    }
}
