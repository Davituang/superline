package com.heiyo.superline.dao.impl;

import com.heiyo.superline.dao.PersonMapper;
import com.heiyo.superline.domain.Person;
import org.springframework.stereotype.Repository;

/**
 * @author txz
 * @date
 */
@Repository
public class PersonMapperImpl implements PersonMapper {
    @Override
    public Person getPersons() {
        Person person = new Person();
        person.setAddress("china");
        person.setAge(18);
        person.setName("tangshan");
        return person;
    }
}

