package com.example.week13.p05;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class JpaRepositoryImpl<T, ID> implements JpaRepository<T, ID> {

    private EntityManager em;

    private Class<T> entityClass;



    public JpaRepositoryImpl(EntityManager em, Class<T> entityClass) {

        this.em = em;

        this.entityClass = entityClass;

    }



    @Override

    public T find(ID id) {

        return em.find(entityClass, id);

    }



    @Override

    public T save(T entity) {

        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            em.persist(entity);

            tx.commit();

            return entity;

        } catch (Exception e) {

            tx.rollback();

            throw e;

        }

    }

}


