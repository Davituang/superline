package com.heiyo.superline.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.heiyo.superline.dao.PersonMapper;
import com.heiyo.superline.domain.Person;
import com.heiyo.superline.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author txz
 * @date
 */
@Service/*(interfaceClass = PersonService.class)*/
@Component
public class PersonServiceImpl implements PersonService {

    private Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonMapper personMapper;

    @Override
    public Person getPersons() {
        logger.info("业务层开始运行---------------------------");
        Person persons = personMapper.getPersons();
        return persons;
    }


}
