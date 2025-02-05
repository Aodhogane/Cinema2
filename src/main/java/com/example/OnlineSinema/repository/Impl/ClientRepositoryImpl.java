package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.Client;
import com.example.OnlineSinema.repository.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryImpl extends BaseRepository<Client> implements ClientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Client findClientByUserId(int userId) {
        return entityManager.createQuery("select c from Client c where c.user.id = :userId", Client.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
