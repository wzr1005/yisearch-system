package com.wzr.yi.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @autor zhenrenwu
 * @date 2022/2/19 7:33 下午
 */
@Data
public class YiUser {

    //id
    private Integer Id;

    //用户账户ID，随机生成且唯一
    //用户账户，用户自己设定
    public String userCount;
    public String passwd;
    public String salt;
    //用户昵称，
    public String nickName;
    public String gender;
    public String teleNum;
    //电话和邮箱
    public String emailAddress;
    public String signature;

    public String avatar_path;
    public String role;
    public String growthScore;
    public String growthLevel;

    //认证系统的真实名字和身份证号码
    public String sfzName;
    public String sfzNumber;
    public String createdTime;



}
