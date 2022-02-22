//package com.wzr.yi.service;
//
//import com.wzr.yi.config.bean.RsaProperties;
//import com.wzr.yi.entity.AuthUserDto;
//import com.wzr.yi.utils.RsaUtils;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @autor zhenrenwu
// * @date 2022/2/19 8:11 下午
// */
//public class RegisterService {
//
//    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUserDto, HttpServletRequest request) throws Exception {
//        // 密码解密
//        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privatekey, authUserDto.getPassword());
//        //查询验证码
////        String code = (String) redisUtils
//
//        return null;
//    }
//}
