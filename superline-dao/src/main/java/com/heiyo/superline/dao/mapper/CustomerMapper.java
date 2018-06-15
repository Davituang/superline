package com.heiyo.superline.dao.mapper;

import com.heiyo.superline.domain.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author txz
 * @date
 *
 */
@Mapper
public interface CustomerMapper {

    @Insert("insert into customer(name,age,address,telephone) values(#{name,jdbcType=VARCHAR},#{age,jdbcType=INTEGER},#{address,jdbcType=VARCHAR},#{telephone,jdbcType=VARCHAR})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void insertCustomer(Customer customer);

    @Select("select * from customer")
    List<Customer> getAllCustomers();



}
