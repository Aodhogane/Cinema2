package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.ClientDTO;
import com.example.OnlineSinema.DTO.inputDTO.ClientInputDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface ClientService {
    String findClintNameByClintId(int clientId);
    ClientDTO findClientById(int clientId);
    Page<ClientDTO> findAllPage(int page, int size);
    void update(ClientDTO clientDTO, int clientId);
    void create(ClientInputDTO clientInputDTO);
    void delete(int actorId);
}
