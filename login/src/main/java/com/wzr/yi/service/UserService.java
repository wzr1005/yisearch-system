package com.wzr.yi.service;

import com.wzr.yi.entity.YiUser;

/**
 * @autor zhenrenwu
 * @date 2022/6/23 1:07 上午
 */
public interface UserService {

    public String isValidRegister(YiUser yiUser);

    public String register(YiUser yiUser);

    public String signIn(YiUser yiUser, String token);





}
