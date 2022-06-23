package com.wzr.yi.config.bean;

import com.wzr.yi.entity.YiUser;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @autor zhenrenwu
 * @date 2022/6/23 2:34 上午
 */
public class loginUtils {

    public static String createJWT(YiUser yiUser){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(yiUser.getUserCount())
                .setSubject(yiUser.getNickName())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "danielwu")
                .setExpiration(new Date(new Date().getTime() + 86400000));

        return jwtBuilder.compact();
    }

    public static void main(String[] args) {
        YiUser yiUser = new YiUser();
        yiUser.setId(1);
        yiUser.setNickName("哈哈呃");
        yiUser.setUserCount("wzr123");

        String jwt = createJWT(yiUser);
        System.out.println(jwt);
    }
}
