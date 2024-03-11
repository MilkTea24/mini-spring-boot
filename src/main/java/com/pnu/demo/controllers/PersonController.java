package com.pnu.demo.controllers;

import com.pnu.demo.entities.Person;
import com.pnu.demo.services.PersonService;
import edu.pnu.myspring.annotations.MyAutowired;
import edu.pnu.myspring.annotations.MyRequestMapping;
import edu.pnu.myspring.annotations.MyRestController;

@MyRestController
public class PersonController {
    @MyAutowired
    private PersonService service;

    public String createPerson(String name, int age) {
        Person person = new Person(name, age);
        service.save(person);
        return "Person created with ID: " + person.getId();
    }

    @MyRequestMapping(value = "/person", method = "GET")
    public String getPerson(Long id) {
        Person person = service.findById(id);
        return (person != null) ? person.toString() : "Person not found!";
    }

}
