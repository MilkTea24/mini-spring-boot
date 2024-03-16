package edu.pnu.cse.repository;

import edu.pnu.cse.annotations.Valid;
import edu.pnu.cse.entities.Student;

public interface StudentRepository {

    public Student save(@Valid Student student);

}
