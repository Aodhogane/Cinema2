package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.Actors;
import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.repository.ActorRepository;
import com.example.OnlineSinema.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {
}
