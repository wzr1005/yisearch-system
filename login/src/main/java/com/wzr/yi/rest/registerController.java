//package com.wzr.yi.rest;
//
//
//import com.wzr.yi.config.bean.SecurityProperties;
//import com.wzr.yi.service.OnlineUserService;
//import com.wzr.yi.utils.RedisUtils;
//import com.wzr.yi.utils.RsaUtils;
//import com.wzr.yi.config.bean.RsaProperties;
//import com.wzr.yi.entity.AuthUserDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @autor zhenrenwu
// * @date 2022/2/19 8:10 下午
// */
//@RestController
//@RequiredArgsConstructor
//public class registerController {
//
//    private final SecurityProperties properties;
//    private final RedisUtils redisUtils;
//    private final OnlineUserService onlineUserService;
//
//
//
//    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUserDto, HttpServletRequest request) throws Exception {
//        // 密码解密
//        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privatekey, authUserDto.getPassword());
//        //查询验证码
////        String code = (String) redisUtils.
//
//        return null;
//    }
//}
