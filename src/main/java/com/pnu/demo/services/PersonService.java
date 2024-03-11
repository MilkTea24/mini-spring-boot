package com.pnu.demo.services;

import com.pnu.demo.entities.Person;
import com.pnu.demo.repositories.PersonRepository;
import edu.pnu.myspring.annotations.MyAutowired;
import edu.pnu.myspring.annotations.MyService;

import java.util.List;

@MyService
public class PersonService {
    @MyAutowired
    private PersonRepository repository;

    public Person save(Person person) {
        return repository.save(person);
    }

    public Person findById(Long id) {
        return repository.findById(id);
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
