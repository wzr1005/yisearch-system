package com.wzr.yi.config.bean;

import lombok.Data;

/**
 * @autor zhenrenwu
 * @date 2022/2/19 10:16 下午
 */
@Data
public class SecurityProperties {

    private String header;
    //令牌前缀
    private String tokenStartWith;

    //必须使用88位的Base64对该令牌进行编码
    private String base64Secret;

    //令牌过期时间
    private Long tokenValidityInSeconds;

    // 在线用户key，根据key查询redis中在线用户对数据
    private String onlineKey;

    //验证码
    private  String codeKey;

    //token续期检查
    private  Long detect;

    //续期时间
    private Long renew;

    public SecurityProperties(String header, String tokenStartWith, String base64Secret, Long tokenValidityInSeconds, String onlineKey, String codeKey, Long detect, Long renew) {
        this.header = header;
        this.tokenStartWith = tokenStartWith;
        this.base64Secret = base64Secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
        this.onlineKey = onlineKey;
        this.codeKey = codeKey;
        this.detect = detect;
        this.renew = renew;
    }
}
