package com.example.OnlineSinema.repository;

import com.example.OnlineSinema.domain.Ticket;

import java.util.List;


public interface TicketRepository {
    void save(Ticket ticket);
    Ticket findById(int id);
    List<Ticket> findByFilmId(int filmId);
    void deleteById(int id);
    void update(Ticket ticket);
    List<Ticket> findByUserId(int userId);
}

