package com.example.week13.p05;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//프록시가 수행해야 할 작업 정의
//JpaRepositoryImpl에 정의된 CRUD를 실행하도록 지시
//CrudMethodMetadataPostProcessor.java
public class RepositoryInvocationHandler<E, ID> implements InvocationHandler {

    private final JpaRepository<E, ID> repository;



    public RepositoryInvocationHandler(EntityManager entityManager, Class<E> entityClass) {

        this.repository = new JpaRepositoryImpl<>(entityManager, entityClass);

    }


    //JpaRepositoryImpl에게 CRUD를 실행하도록 지시
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Method의 class : " + method.getDeclaringClass().getSimpleName());
        System.out.println("repository의 class : " + repository.getClass().getSimpleName());
        Class<?> repositoryImplClass = repository.getClass();
        Method[] methods = repositoryImplClass.getDeclaredMethods();
        Object result;
        for (Method m : methods) {
            if (method.getName().equals(m.getName())) {
                result = m.invoke(repository, args);
                return result;
            }
        }

        return null;
    }

}