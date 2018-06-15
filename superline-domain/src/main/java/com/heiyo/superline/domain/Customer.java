package com.heiyo.superline.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Customer implements Serializable{

    private static final long serialVersionUID = 4268775181053852837L;

    private Long id;

    @NotEmpty(message = "name can not be blank")
    private String name;
    @Max(value = 150,message = "an error age,please check it")
    private Integer age;
    @NotEmpty(message = "address can not be blank")
    @Size(min = 6,max = 600,message = "address too short or too long")
    private String address;
    @NotBlank(message = "telephone is required")
    private String telephone;


    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
