package com.example.OnlineSinema.repository.impl;

import com.example.OnlineSinema.domain.User;
import com.example.OnlineSinema.exceptions.UserNotFound;
import com.example.OnlineSinema.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(User user) {
        if (entityManager.contains(user)) {
            entityManager.merge(user);
        } else {
            entityManager.persist(user);
        }
    }

    @Override
    public void update(User user) {
        User existingUser = findById(user.getId());
        if (existingUser == null) {
            throw new UserNotFound("User with ID: " + user.getId() + " not found");
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setAccess(user.getAccess());
        existingUser.setReviewsList(user.getReviewsList());
        existingUser.setTicketsList(user.getTicketsList());

        entityManager.merge(existingUser);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        User user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByName(String name) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE LOWER(u.name) = LOWER(:name)", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findById(int id) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        long userCount = entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class)
                .getSingleResult();

        List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(users, pageable, userCount);
    }

    @Override
    public boolean existsByEmail(String email) {
        Long count = (Long) entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email")
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean existsByUsername(String username) {
        Long count = (Long) entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username")
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();

        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }
}