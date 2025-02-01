package com.example.OnlineSinema.service;

import com.example.OnlineSinema.DTO.DirectorDTO;
import org.springframework.data.domain.Page;

public interface DirectorService {
    DirectorDTO findById(int directorId);

    Page<DirectorDTO> findAllPage(int page, int size);
}
