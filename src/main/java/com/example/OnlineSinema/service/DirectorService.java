package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.DirectorDTO;
import com.example.OnlineSinema.DTO.inputDTO.DirectorInputDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface DirectorService {
    DirectorDTO findById(int directorId);
    Page<DirectorDTO> findAllPage(int page, int size);
    void update(DirectorDTO directorDTO, int directorId);
    void create(DirectorInputDTO directorInputDTO);
    void delete(int directorId);
}
