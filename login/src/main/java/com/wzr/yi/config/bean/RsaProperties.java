package com.wzr.yi.config.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @autor zhenrenwu
 * @date 2022/2/19 10:09 下午
 */
@Data
@Component

public class RsaProperties {

    public static String privatekey;

    @Value("${rsa.private_key}")
    public void setPrivatekey(String privatekey){
        RsaProperties.privatekey = privatekey;
    }
}
