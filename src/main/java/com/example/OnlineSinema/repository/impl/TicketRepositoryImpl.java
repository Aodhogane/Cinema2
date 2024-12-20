package com.example.OnlineSinema.repository.impl;

import com.example.OnlineSinema.domain.Ticket;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.TicketRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Ticket ticket) {
        if (entityManager.contains(ticket)) {
            entityManager.merge(ticket);
        } else {
            entityManager.persist(ticket);
        }
    }

    @Override
    public Ticket findById(int id) {
        try {
            return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.id = :id", Ticket.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Ticket> findByFilmId(int filmId) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.film.id = :filmId", Ticket.class)
                .setParameter("filmId", filmId)
                .getResultList();
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Ticket ticket = findById(id);
        if (ticket != null) {
            entityManager.remove(ticket);
        }
    }

    @Override
    @Transactional
    public void update(Ticket ticket) {
        Ticket existingTicket = findById(ticket.getId());
        if (existingTicket == null) {
            throw new UserNotFound("Ticket with ID: " + ticket.getId() + " not found");
        }

        existingTicket.setPurchaseDate(ticket.getPurchaseDate());
        existingTicket.setPrice(ticket.getPrice());

        entityManager.merge(existingTicket);
    }

    @Override
    public List<Ticket> findByUserId(int userId) {
        return entityManager.createQuery("SELECT t FROM Ticket t WHERE t.user.id = :userId", Ticket.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}