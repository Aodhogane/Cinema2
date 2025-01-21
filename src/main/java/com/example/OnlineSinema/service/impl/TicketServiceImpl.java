package com.example.OnlineSinema.service.impl;

import com.example.OnlineSinema.domain.Film;
import com.example.OnlineSinema.domain.Ticket;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.dto.ticketDTO.TicketOutputDTO;
import com.example.OnlineSinema.exceptions.FilmNotFounf;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.FilmRepository;
import com.example.OnlineSinema.repository.TicketRepository;
import com.example.OnlineSinema.repository.UserRepository;
import com.example.OnlineSinema.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, FilmRepository filmRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(TicketOutputDTO ticketOutputDTO) {
        User user = userRepository.findById(ticketOutputDTO.getUserId());
        if (user == null) {
            throw new UserNotFound("User with ID: " + ticketOutputDTO.getUserId() + " not found");
        }

        Film film = filmRepository.findById(ticketOutputDTO.getFilmId());
        if (film == null) {
            throw new FilmNotFounf("Film with ID: " + ticketOutputDTO.getFilmId() + " not found");
        }

        Ticket ticket = new Ticket(ticketOutputDTO.getPrice(), ticketOutputDTO.getPurchaseDate(), user, film);
        ticketRepository.save(ticket);
    }

    @Override
    public TicketOutputDTO findById(int id) {
        Ticket ticket = ticketRepository.findById(id);
        if (ticket == null) {
            throw new UserNotFound("Ticket with ID: " + id + " not found");
        }
        return modelMapper.map(ticket, TicketOutputDTO.class);
    }

    @Override
    public void update(int id, Float price, LocalDateTime purchaseDate) {
        Ticket ticket = ticketRepository.findById(id);
        if (ticket == null) {
            throw new UserNotFound("Ticket with ID: " + id + " not found");
        }

        ticket.setPrice(price);
        ticket.setPurchaseDate(purchaseDate);
        ticketRepository.save(ticket);
    }

    @Override
    public void deleteById(int id) {
        Ticket ticket = ticketRepository.findById(id);
        if (ticket == null) {
            throw new UserNotFound("Ticket with ID: " + id + " not found");
        }
        ticketRepository.deleteById(id);
    }

    @Override
    public List<TicketOutputDTO> findByUserId(int userId) {
        List<Ticket> tickets = ticketRepository.findByUserId(userId);
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketOutputDTO.class))
                .toList();
    }

    @Override
    public List<TicketOutputDTO> findByFilmId(int filmId) {
        List<Ticket> tickets = ticketRepository.findByFilmId(filmId);
        return tickets.stream()
                .map(ticket -> modelMapper.map(ticket, TicketOutputDTO.class))
                .toList();
    }
}