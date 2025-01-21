package com.example.OnlineSinema.service;

import com.example.OnlineSinema.dto.directorDTO.DirectorOutputDTO;
import com.example.OnlineSinema.dto.directorDTO.DirectorsInfoDto;
import com.example.OnlineSinema.dto.filmDTO.FilmOutputDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DirectorsService {
    void save(DirectorOutputDTO directorOutputDTO);
    List<DirectorOutputDTO> findAll();
    List<DirectorOutputDTO> findByFilmId(int id);
    DirectorOutputDTO findById(int id);
    void deleteById(int id);
    void update(int id, String surname, String name, String MiddleName);
    Page<DirectorsInfoDto> findAll(int page, int size);
    List<FilmOutputDTO> findFilmsByDirectorId(int directorId);
}
