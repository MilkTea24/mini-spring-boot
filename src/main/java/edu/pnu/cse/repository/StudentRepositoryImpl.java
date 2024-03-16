package edu.pnu.cse.repository;

import edu.pnu.cse.annotations.Valid;
import edu.pnu.cse.entities.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentRepositoryImpl implements StudentRepository{

    private final Map<String, Student> students = new HashMap<>();



    public Student save(@Valid Student student) {

        return students.put(student.getId(), student);

    }

}
