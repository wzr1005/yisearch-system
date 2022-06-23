package com.wzr.yi.config.bean;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @autor zhenrenwu
 * @date 2022/6/23 3:34 下午
 */
public class MD5Utils {
    private static final String salt = "1a2b3c4d";

    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 第一次加密，生成第二个salt
     * @param inputPass
     * @return
     */
    public static String inputPassToFromPass(String inputPass) {
        // salt可以随机的放在输入密码的各个部分当中
        String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次加密, 使用的盐需要重新生成（这个盐会存放到数据库中，成为用户的一个属性）专门为每一个用户随机生成的
     * @param formPass
     * @param userSalt
     * @return
     */
    public static String formPassToDBPass(String formPass, String userSalt) {
        String str = userSalt.charAt(0) + userSalt.charAt(2) + formPass + userSalt.charAt(5) + userSalt.charAt(4);
        return md5(str);
    }

    /**
     * 将第一次加密和第二次加密调用
     * @param inputPass
     * @param userSalt
     * @return
     */
    public static String inputPassToDBPass(String inputPass, String userSalt) {
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = formPassToDBPass(fromPass, userSalt);
        return dbPass;
    }

    public static boolean validInputPass(String inputPass, String userSalt, String dbPass){
        return dbPass.equals(inputPassToDBPass(inputPass, userSalt));
    }

    public static void main(String[] args) {
        // 将密码第一次加密的结果和salt运算，存入数据库。
        String yici = inputPassToFromPass("123456");
        System.out.println(yici);
        System.out.println(formPassToDBPass(yici, "abcdefgh"));
        // 输入密码，和salt一起验证是否正确
        String dbPass = "d1dd41e1feba61f5bd30d1eb98852fa3";
        String dbSalt = "DWSV2XiDdp";
        String input = "123";
        System.out.println(validInputPass(input, dbSalt, dbPass));
    }


}
