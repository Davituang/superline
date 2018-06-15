package com.heiyo.superline.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author txz
 * @date
 */
@Component
@ConfigurationProperties(prefix = "myshop")
public class MyShopConfig {

    public static ArrayList<Integer> ids = new ArrayList<>();

    public void setIds(ArrayList<Integer> ids) {
        MyShopConfig.ids = ids;
    }
}
