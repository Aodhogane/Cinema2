package com.example.OnlineSinema.service;

import com.example.OnlineSinema.dto.genresDTO.GenresOutputDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface GenresService {
    void save(GenresOutputDTO genresOutputDTO);
    List<GenresOutputDTO> findAll();
    GenresOutputDTO findById(int id);
    void deleteById(int id);
    void update(int id, String name);
    Page<GenresOutputDTO> findAll(int page, int size);
}
