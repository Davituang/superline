package com.heiyo.superline.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.heiyo.superline.dao.mapper.CustomerMapper;
import com.heiyo.superline.domain.Customer;
import com.heiyo.superline.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public List<Customer> getAll() {
        List<Customer> customers = customerMapper.getAllCustomers();
        return customers;
    }
}
