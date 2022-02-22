package com.wzr.yi.entity;

import lombok.Data;

/**
 * @autor zhenrenwu
 * @date 2022/2/19 8:35 下午
 */
@Data
public class AuthUserDto {
    private String userCount;
    private String password;
    //验证码
    private String code;
    private String token;


}
