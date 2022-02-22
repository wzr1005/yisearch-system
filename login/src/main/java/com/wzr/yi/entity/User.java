package com.wzr.yi.entity;

/**
 * @autor zhenrenwu
 * @date 2022/2/19 7:33 下午
 */
public class User {

    //id
    private Integer Id;

    //用户账户ID，随机生成且唯一
    private String userId;
    //用户账户，用户自己设定
    private String userCount;
    //用户昵称，
    private String nickName;
    private String gender;
    private String avatar_path;
    private String role;
    //用户所在群组
    private String group;




    //认证系统的真实名字和身份证号码
    private String sfzName;
    private String sfzNumber;
    //电话和邮箱
    private String teleNum;
    private String emailAddress;

}
