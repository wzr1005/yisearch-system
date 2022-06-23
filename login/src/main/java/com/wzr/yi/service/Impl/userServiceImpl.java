package com.wzr.yi.service.Impl;

//import com.wzr.common.config.DruidLoginPool;
import com.alibaba.fastjson.JSONObject;
import com.wzr.yi.config.bean.DruidLoginPool;
import com.wzr.yi.config.bean.RsaProperties;
import com.wzr.yi.entity.AuthUserDto;
import com.wzr.yi.entity.YiUser;
import com.wzr.yi.service.UserService;
import com.wzr.yi.utils.RsaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.wzr.common.constant.UserConstant.*;
import static com.wzr.yi.utils.MysqlUtils.generateSqlInsert;

/**
 * @autor zhenrenwu
 * @date 2022/6/23 1:07 上午
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class userServiceImpl implements UserService {

    private final DruidLoginPool druidLoginPool;

    private final RsaUtils rsaUtils;
    @Override
    public String isValidRegister(YiUser yiUser) {
        Field[] fields = YiUser.class.getDeclaredFields();
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(yiUser);
        for(Field field : fields){
            String key = field.getName();
            if(!judgeStringList(validUserFields, key)) continue;
            String value;
            value = (String) jsonObject.get(key);
            if(value.equals("")) continue;
            String sql = String.format("select count(*) count from yiuser where userCount = '%s'", value);
            List<Map<String, Object>> mapList = druidLoginPool.executeSqlQuery(sql);
            Map<String, Object> mp = mapList.get(0);
            long cnt = (long) mp.get("count");
            if(cnt != 0){
                return String.format("%s is used!", key);
            }
        }
        return "valid";
    }

    @Override
    public String register(YiUser yiUser)  {


        return "注册失败！";
    }

    @Override
    public String signIn(YiUser yiUser, String token) {
        //先去redis里查token

        return null;
    }

    public static boolean judgeStringList(String[] strings, String value){
        for (int i = 0; i < strings.length; i++) {
            if(value.equals(strings[i])) return true;
        }
        return false;
    }
}
