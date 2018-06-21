package com.heiyo.superline.web.controller;

import com.heiyo.superline.common.utils.BeanUtils;
import com.heiyo.superline.domain.Customer;
import com.heiyo.superline.domain.CustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author txz
 * @date
 */
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate template;// 当存储的值为字符串时使用如list<String>

    @Autowired
    private RedisTemplate<String, Object> redisObjectTemplate;// 当存储的值为自定义对象时使用,如List<Customer>

    /*以key-value形式存储------------------------------------------------------------------------------------------------*/

    @RequestMapping("/set/{name}")
    public @ResponseBody
    String setStringToRedis(@PathVariable String name) {
        if (!template.hasKey("name")) {
            template.opsForValue().append("name", name);
            return "opreator success!";
        } else {
            template.delete("name");
            return "key 已经存在！";
        }
    }

    @RequestMapping("/addCustomer")
    public @ResponseBody
    Customer addCustomer(@RequestBody Customer customer) {
        /*if (redisObjectTemplate.hasKey("customer")) {
            redisObjectTemplate.delete("customer");
            return "对象已经存在";
        }*/
        Customer lastCustomer = (Customer) redisObjectTemplate.opsForValue().getAndSet("customer", customer);
        System.out.println(lastCustomer);
        return lastCustomer;
    }

    @RequestMapping("/getCustomer")
    public @ResponseBody
    Customer getCustomer() {
        Object customer = redisObjectTemplate.opsForValue().get("customer");
        if (customer == null) {
            return null;
        }

        return (Customer) customer;


    }


    /*以list形式存储---------------------------------------------------------------------------------------------------*/
    @RequestMapping("/addlist")
    public @ResponseBody
    String addList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("tang");
        list.add("fang");
        list.add("zhang");
        for (String name : list) {
            System.out.println(name);
            template.opsForList().leftPush("listname", name);
        }

        return "success";


    }

    @RequestMapping("/getlist")
    public @ResponseBody
    List getlistName() {
        if (template.opsForList().size("listname") > 0) {
            List<String> listname = template.opsForList().range("listname", 0, -1);
            return listname;
        }
        return null;

    }

    @RequestMapping("/addlistCustomer")
    public @ResponseBody
    String addListCustomer(@RequestBody CustomerVo customerVo) {
        List<Customer> customers = customerVo.getCustomers();
        if (redisObjectTemplate.hasKey("listCustomer")) {
            redisObjectTemplate.delete("listCustomer");
            return "对象已存在";
        }

        for (Customer customer : customers) {
            redisObjectTemplate.opsForList().leftPush("listCustomer", customer);
        }

        return "success";

    }


    @RequestMapping("/getlistCustomer")
    public @ResponseBody
    List<Customer> getlistCustomer() {
        int size = (int) (long) redisObjectTemplate.opsForList().size("listCustomer");
        if (size > 0) {
            List<Object> list = redisObjectTemplate.opsForList().range("listCustomer", 0, -1);
//            List<Customer> listCustomer = Arrays.asList(redisObjectTemplate.opsForList().range("listCustomer", 0, -1).toArray(new Customer[size]));
//            return listCustomer;
            Customer[] customers = list.toArray(new Customer[size]);
            List<Customer> listCustomers = Arrays.asList(customers);
            return listCustomers;
        }

        return null;


    }


    /*以set形式存储----------------------------------------------------------------------------------------------------*/
    @RequestMapping("/addSetName/{name}")
    public @ResponseBody
    String addSetName(@PathVariable String name) {
        Boolean setname = template.opsForSet().isMember("setname", name);
        if(setname){
            return "该成员已存在集合中。";
        }
        template.opsForSet().add("setname", name);
        template.opsForZSet().add("zsetname",name,1D);

        return "success";
    }

    @RequestMapping("/getSetName")
    public @ResponseBody Set<String> getSetName(){
        Set<String> setname = template.opsForSet().members("setname");
        String setname1 = template.opsForSet().randomMember("setname");
        System.out.println("随机返回的一个成员为："+setname1);

        List<String> setname2 = template.opsForSet().randomMembers("setname", 2L);
        System.out.println("随机返回的多个成员为："+setname2);

        Set<String> setname3 = template.opsForSet().distinctRandomMembers("setname", 2L);
        System.out.println("随机返回的多个不重复成员为："+setname3);

        String setname4 = template.opsForSet().pop("setname");
        System.out.println("pop成员"+setname4);

        /*List<String> pop = template.opsForSet().pop("setname",3L);
        System.out.println(pop);*/

        return setname;

    }

    @RequestMapping("/addSetCustomer")
    public @ResponseBody String addSetCustomer(@RequestBody CustomerVo customerVo){
        if(customerVo==null||customerVo.getCustomers().size()==0){
            return "请求参数为空。";
        }
        List<Customer> customers = customerVo.getCustomers();
        for (Customer customer:customers){
            redisObjectTemplate.opsForSet().add("setcustomer",customer);
        }

        return "success";
    }


    @RequestMapping("/getSetCustomer")
    public @ResponseBody List<Customer> getSetCustomer(){
        Set<Object> setcustomer = redisObjectTemplate.opsForSet().members("setcustomer");
        if(setcustomer==null||setcustomer.size()==0){
            return null;
        }
        Customer[] customers = setcustomer.toArray(new Customer[setcustomer.size()]);
        return Arrays.asList(customers);

    }


    /*以hash形式存储---------------------------------------------------------------------------------------------------*/
    @RequestMapping("/hashname")
    public @ResponseBody String addhashName(@RequestBody Customer customer){
        try {
            Map map = BeanUtils.convertBean(customer);
            Set set = map.keySet();
            for (Object object:set){
                redisObjectTemplate.opsForHash().put("hashname",object,map.get(object));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return "success";
    }


    @RequestMapping("/gethashname")
    public @ResponseBody Customer gethashname(){
        Map<Object, Object> hashname = redisObjectTemplate.opsForHash().entries("hashname");
        try {
            Customer customer = (Customer) BeanUtils.convertMap(Customer.class, hashname);
            return customer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }



}
