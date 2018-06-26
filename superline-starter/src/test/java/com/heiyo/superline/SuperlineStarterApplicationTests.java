package com.heiyo.superline;

import com.heiyo.superline.dao.mapper.CustomerMapper;
import com.heiyo.superline.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperlineStarterApplicationTests {

	@Autowired
	private CustomerMapper customerMapper;

	@Test
	public void contextLoads() {

		List<Customer> allCustomers = customerMapper.getAllCustomers();
		System.out.println(allCustomers);


	}

}
