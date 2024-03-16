package com.example.week13.p05;

public interface JpaRepository<T, ID> {

    T find(ID id);

    T save(T entity);

}