package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {
    private final PersonDao personDao;
    @Autowired
    public PersonService(@Qualifier("fakedao") PersonDao personDao) {
        this.personDao = personDao;
    }

    public int addPerson(Person person){
        return personDao.addPerson(person);
    }
    public List<Person> getAllPeople(){
       return personDao.getAllPerson();
    }
    public Optional<Person> findIfPresent(UUID id){
        return personDao.isPresent(id);

    }
    public int delete(UUID id){
        return personDao.delete(id);
    }
    public int update(UUID id,Person person){
        return personDao.update(id,person);
    }

}
