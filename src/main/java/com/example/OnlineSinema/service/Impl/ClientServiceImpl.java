package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.ClientDTO;
import com.example.OnlineSinema.DTO.inputDTO.ClientInputDTO;
import com.example.OnlineSinema.domain.Client;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.enums.UserRoles;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.repository.ClientRepository;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             ModelMapper modelMapper, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public String findClintNameByClintId(int clientId) {
        Client client = clientRepository.findById(Client.class, clientId);
        return client.getName();
    }

    @Override
    public ClientDTO findClientById(int clientId){
        Client client = clientRepository.findById(Client.class, clientId);

        if (client == null){
            throw new FilmNotFounf();
        }

        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public Page<ClientDTO> findAllPage(int page, int size){
        Page<Client> clients = clientRepository.findAllPage(Client.class, page, size);

        List<ClientDTO> clientDTOList = new ArrayList<>();
        for (Client client : clients){
            ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
            clientDTOList.add(clientDTO);
        }

        return new PageImpl<>(clientDTOList, PageRequest.of(page - 1, size), clients.getTotalElements());
    }

    @Override
    public void update(ClientDTO clientDTO, int clientId){
        Client clientOld = clientRepository.findById(Client.class, clientId);
        Client client = modelMapper.map(clientDTO, Client.class);

        client.setUser(clientOld.getUser());
        client.setId(clientId);
        clientRepository.update(client);
    }

    @Override
    @Transactional
    public void create(ClientInputDTO clientInputDTO){
        User user = new User(clientInputDTO.getEmail(), clientInputDTO.getPassword(), UserRoles.CLIENT);
        Client client = modelMapper.map(clientInputDTO, Client.class);

        userRepository.create(user);
        client.setUser(user);
        clientRepository.create(client);
    }

    @Override
    public void delete(int clientId){
        Client client = clientRepository.findById(Client.class, clientId);
        clientRepository.delete(client);
    }
}
