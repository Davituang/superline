package com.heiyo.superline.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txz
 * @date
 */
public class CustomerVo extends Customer {

    private List<Customer> customers = new ArrayList<>();

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
