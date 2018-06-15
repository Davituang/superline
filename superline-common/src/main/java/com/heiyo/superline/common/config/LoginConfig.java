package com.heiyo.superline.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 获取map属性
 * @author txz
 * @date
 */
@ConfigurationProperties(prefix = "myenvironment")
@Component
public class LoginConfig {

    public static HashMap<String,String> login = new HashMap<>();

    public void setLogin(HashMap<String, String> login) {
        LoginConfig.login = login;
    }

}
