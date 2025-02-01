package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.ClientDTO;
import org.springframework.data.domain.Page;

public interface ClientService {
    String findClintNameByClintId(int clientId);
    ClientDTO findClientById(int clientId);

    Page<ClientDTO> findAllPage(int page, int size);
}
