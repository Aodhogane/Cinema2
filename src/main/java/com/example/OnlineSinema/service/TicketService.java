package com.example.OnlineSinema.service;

import com.example.OnlineSinema.dto.ticketDTO.TicketOutputDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketService {
    void save (TicketOutputDTO ticketOutputDTO);
    TicketOutputDTO findById(int id);
    void update (int id, Float price, LocalDateTime purchaseDate);
    void deleteById(int id);
    List<TicketOutputDTO> findByUserId(int userId);
    List<TicketOutputDTO> findByFilmId(int filmId);
}
