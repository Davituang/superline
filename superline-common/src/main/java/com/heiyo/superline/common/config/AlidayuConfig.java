package com.heiyo.superline.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 简单配置类型使用@value读取
 *
 * @author txz
 * @date
 */
@Configuration
public class AlidayuConfig {

    public static String authKey;

    public static String password;

    @Value("${alibaba.alidayu.authKey}")
    public void setAuthKey(String authKey) {
        if(StringUtils.isBlank(authKey)){
            throw new RuntimeException("authKey can not be blank！");
        }
        AlidayuConfig.authKey = authKey;
    }

    @Value("${alibaba.alidayu.password}")
    public void setPassword(String password) {
        if(StringUtils.isBlank(password)){
            throw new RuntimeException("password can not be blank！");
        }
        AlidayuConfig.password = password;
    }

}
