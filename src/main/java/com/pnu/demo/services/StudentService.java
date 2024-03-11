package com.pnu.demo.services;

import com.pnu.demo.entities.Student;
import com.pnu.demo.repositories.StudentRepository;
import edu.pnu.myspring.annotations.MyAutowired;
import edu.pnu.myspring.annotations.MyService;

import java.util.List;

@MyService
public class StudentService {
    @MyAutowired
    private StudentRepository repository;

    public Student save(Student student) {
        return repository.save(student);
    }

    public Student findById(Long id) {
        return repository.findById(id);
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
