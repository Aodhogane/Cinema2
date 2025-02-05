package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientRepository {
    List<Client> getAll(Class<Client> entityClass);
    Page<Client> findAllPage(Class<Client> entityClass, int page, int size);
    Client findById(Class<Client> entityClass, int id);
    void create(Client entity);
    void update(Client entity);
    void delete(Client entity);

    Client findClientByUserId(int userId);
}
