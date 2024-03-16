package com.pnu.demo.controllers;

import com.pnu.demo.entities.Student;
import com.pnu.demo.services.StudentService;
import edu.pnu.myspring.annotations.*;

@MyRestController
public class StudentController {
    @MyAutowired
    private StudentService service;

    @PostMapping(value = "/students")
    public String createStudent(String name, String course) {
        Student student = new Student(name, course);
        service.save(student);
        return "Student created with ID: " + student.getId();
    }

    @MyRequestMapping(value = "/students/{id}")
    public String getStudent(@PathVariable("id") Long id) {
        Student student = service.findById(id);
        return (student != null) ? student.toString() : "Student not found!";
    }

}
