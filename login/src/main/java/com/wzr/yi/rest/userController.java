package com.wzr.yi.rest;

import com.wzr.yi.config.bean.MD5Utils;
import com.wzr.yi.entity.YiUser;
import com.wzr.yi.service.UserService;
import com.wzr.yi.common.config.DruidPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.wzr.yi.common.utils.MysqlUtils.generateSqlInsert;


/**
 * @autor zhenrenwu
 * @date 2022/6/23 12:31 上午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class userController {

    private final UserService userService;

    private final DruidPool druidPool;

    @Resource
    private RedisTemplate redisTemplate;

    private final RestTemplate restTemplate;
    // 注册,
    @PostMapping("/register")
    public String register(@RequestBody YiUser yiUser){
        //验证usercount和telenum是否有重复
        String userSalt = RandomStringUtils.randomAlphanumeric(10);
        yiUser.setSalt(userSalt);
        yiUser.setPasswd(MD5Utils.inputPassToDBPass(yiUser.getPasswd(), yiUser.getSalt()));
        String response = userService.isValidRegister(yiUser);
        if(!response.equals("valid")){
           return response;
        }
        String sql = generateSqlInsert(yiUser);
        if(druidPool.executeSqlUpdate(sql)){
            return "注册成功！";

        }
        return "注册失败";
    }

    @PostMapping("/sign")
    public String sign(@RequestBody YiUser yiUser, HttpServletRequest request){
        //先查询是否存在
        String token = request.getHeader("token");
        Object o = redisTemplate.opsForValue().get(token);

        if(o == null){
            redisTemplate.opsForValue().set(token, "{}", 7*24*3600L, TimeUnit.SECONDS);
        }
        // 用账户密码登录
        String sql = String.format("select passwd, salt from yiuser where userCount = '%s'", yiUser.getUserCount());
        List<Map<String, Object>> list = druidPool.executeSqlQuery(sql);
        String passwd = (String) list.get(0).get("passwd");
        String salt = (String) list.get(0).get("salt");
        if(MD5Utils.validInputPass(yiUser.getPasswd(), salt, passwd)){

            return "登录成功";
        }
        return "登录失败";
    }

    @PostMapping("/logout")
    public String logout(@RequestBody YiUser yiUser, HttpServletRequest request){
        String token = request.getHeader("Postman-Token");
        redisTemplate.delete(token);
        return "退出成功！";
    }

    @GetMapping("/testLogin")
    public String testLogin(){

        log.info("调用kankan服务的类");
        String ans = restTemplate.getForObject("http://localhost:8888/kankan/testKankan", String.class);
        return ans;
    }
}
