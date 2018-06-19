package com.heiyo.superline.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.heiyo.superline.dao.mapper.CustomerMapper;
import com.heiyo.superline.domain.Customer;
import com.heiyo.superline.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author txz
 * @date
 */
@Component
@Service(interfaceClass = CustomerService.class)
public class CusotmerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public List<Customer> getAll() {
        List<Customer> customers = customerMapper.getAllCustomers();
        return customers;
    }
}
