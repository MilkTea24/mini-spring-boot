package com.pnu.demo.repositories;

import com.pnu.demo.entities.Person;
import edu.pnu.myspring.annotations.MyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@MyRepository
public class PersonRepository {
    private Map<Long, Person> dataStore = new HashMap<>();
    private AtomicLong counter = new AtomicLong(1);

    public Person save(Person person) {
        long id = counter.getAndIncrement();
        person.setId(id);
        dataStore.put(id, person);
        return person;
    }

    public Person findById(Long id) {
        return dataStore.get(id);
    }

    public List<Person> findAll() {
        return new ArrayList<>(dataStore.values());
    }

    public void delete(Long id) {
        dataStore.remove(id);
    }

}
