package com.pnu.demo.repositories;

import com.pnu.demo.entities.Student;
import edu.pnu.myspring.annotations.MyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@MyRepository
public class StudentRepository {
    private Map<Long, Student> dataStore = new HashMap<>();
    private AtomicLong counter = new AtomicLong(1);

    public Student save(Student student) {
        long id = counter.getAndIncrement();
        student.setId(id);
        dataStore.put(id, student);
        return student;
    }

    public Student findById(Long id) {
        return dataStore.get(id);
    }

    public List<Student> findAll() {
        return new ArrayList<>(dataStore.values());
    }

    public void delete(Long id) {
        dataStore.remove(id);
    }

}

