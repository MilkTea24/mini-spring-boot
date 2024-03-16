package com.example.week13.p05;

import jakarta.persistence.EntityManager;

import java.lang.reflect.Proxy;

//ClubRepository(사용하는 리파지토리 -> jpaRepositoryImpl 상속해야 함) <-> JpaRepositoryImpl(기본적인 CRUD 구현됨) <-> JpaRepository(CRUD 명세 정의)
public class RepositoryFactoryBean<T, E, ID> {
    //T: clubRepository.class
    //E: club.class
    //ID: long

    private final Class<T> repositoryInterface;

    private final Class<E> entityClass;

    private final EntityManager entityManager;



    public RepositoryFactoryBean(Class<T> repositoryInterface, Class<E> entityClass, EntityManager entityManager) {

        this.repositoryInterface = repositoryInterface;

        this.entityClass = entityClass;

        this.entityManager = entityManager;

    }



    public T createRepository() {
        // Implement your code
        T repository = (T) Proxy.newProxyInstance(
                repositoryInterface.getClassLoader(), //프록시 객체를 저장할 클래스 로더
                new Class[]{repositoryInterface}, //프록시가 구현할 인터페이스(ex: ClubRepository)
                new RepositoryInvocationHandler(entityManager, entityClass) //프록시에게 어떤 작업을 수행할지 전달함
        );
        return repository;
    }

}
