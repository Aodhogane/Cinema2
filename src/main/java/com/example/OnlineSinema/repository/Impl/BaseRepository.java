package com.example.OnlineSinema.repository.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public abstract class BaseRepository<Entity> {

    @PersistenceContext
    protected EntityManager entityManager;

    public List<Entity> getAll(Class<Entity> entityClass){
        return entityManager.createQuery("select e from " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public Page<Entity> findAllPage(Class<Entity> entityClass, int page, int size){
        long total = entityManager.createQuery("select count(e) from " + entityClass.getName() + " e", Long.class)
                .getSingleResult();

        List<Entity> entities = entityManager.createQuery("FROM " + entityClass.getName() + " e", entityClass)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();

        return new PageImpl<>(entities, PageRequest.of(page - 1, size), total);
    }

    public Entity findById(Class<Entity> entityClass, int id){
        try {
            return entityManager.find(entityClass, id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public void create(Entity entity){
        try{
            entityManager.persist(entity);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void update(Entity entity){
        try {
            entityManager.merge(entity);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(Entity entity){
        try {
            entityManager.remove(entity);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
