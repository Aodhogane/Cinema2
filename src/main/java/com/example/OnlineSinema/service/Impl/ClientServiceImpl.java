package com.example.OnlineSinema.service.Impl;

import com.example.OnlineSinema.DTO.ClientDTO;
import com.example.OnlineSinema.domain.Client;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.repository.ClientRepository;
import com.example.OnlineSinema.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
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
}
