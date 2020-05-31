package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository("fakedao")
public class FakePersonDataAccessService implements PersonDao {
    private static List<Person> DB=new ArrayList<>();
    public final JdbcTemplate template;
    @Autowired
    public FakePersonDataAccessService(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int addPerson(UUID id, Person person) {
        DB.add(new Person(id,person.getName()));
        return 1;
    }

    @Override
    public List<Person> getAllPerson() {
        final String sql="SELECT id,name FROM person";
        return template.query(sql,(resultSet,i) -> {
            UUID id=UUID.fromString(resultSet.getString("id"));
            String name=resultSet.getString("name");
            return new Person(id,name);
        });

    }

    @Override
    public Optional<Person> isPresent(UUID id) {
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public int delete(UUID id) {
        Optional<Person> optionalPerson=isPresent(id);
        if(optionalPerson.isEmpty()){
            return 0;
        }
        DB.remove(optionalPerson.get());
        return 1;
    }

    @Override
    public int update(UUID id,Person person) {
        return isPresent(id).map(p ->{int index=DB.indexOf(person);
        if(index>=0){
        DB.set(index,person);
        }
        return 1;
        } ).orElse(0);

    }
}
