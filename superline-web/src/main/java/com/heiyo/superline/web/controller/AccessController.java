package com.heiyo.superline.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heiyo.superline.common.config.AlidayuConfig;
import com.heiyo.superline.common.config.LoginConfig;
import com.heiyo.superline.common.config.MyShopConfig;
import com.heiyo.superline.domain.Customer;
import com.heiyo.superline.domain.Person;
import com.heiyo.superline.service.CustomerService;
import com.heiyo.superline.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txz
 * @date
 */
@Controller
public class AccessController {

    @Reference(/*interfaceClass = PersonService.class,*/check = false)
    private PersonService personService;

    @Reference(interfaceClass = CustomerService.class,check = false)
    private CustomerService customerService;

    @RequestMapping("/show")
    public @ResponseBody List<Customer> show(){
        Person persons = personService.getPersons();
        System.out.println(persons.toString());
        List<Customer> customers = customerService.getAll();

        return customers;
    }

    @RequestMapping("/alidayu")
    public void getAlidayu(HttpServletResponse response){

        try {
            response.getWriter().print("authkey:"+AlidayuConfig.authKey+",password:"+AlidayuConfig.password);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/login")
    public @ResponseBody HashMap<String,String> getLoginParameter(){
        HashMap<String, String> login = LoginConfig.login;
        return login;
    }

    @RequestMapping("ids")
    public @ResponseBody List<Integer> getIds(){
        ArrayList<Integer> ids = MyShopConfig.ids;
        return ids;
    }

    @RequestMapping(value = "/addCustomer")
    public void addCustomer(@Validated Customer customer, BindingResult br,HttpServletResponse response){
        String message = "";
        if(br.hasErrors()){
            List<FieldError> fieldErrors = br.getFieldErrors();
            for (FieldError error:fieldErrors ) {
                message = message.concat(","+error.getDefaultMessage());
            }

        }else{
            message="success";
        }

        try {
            response.getWriter().print(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
