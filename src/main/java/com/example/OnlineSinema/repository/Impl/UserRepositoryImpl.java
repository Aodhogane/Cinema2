package com.example.OnlineSinema.repository.Impl;

import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUserByEmail(String email){
        String jpql = "select u from User u where u.email = :email";
        try {
            return entityManager.createQuery(jpql, User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
