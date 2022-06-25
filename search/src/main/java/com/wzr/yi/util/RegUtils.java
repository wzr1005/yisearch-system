package com.wzr.yi.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wzr.yi.common.constant.Constant.ImpurityArr;
import static com.wzr.yi.common.constant.Constant.resourceListCN;

/**
 * @autor zhenrenwu
 * @date 2022/6/10 12:17 下午
 */
public class RegUtils {

    public static String matchYear(String query){
        String res = "";
        Pattern pattern = Pattern.compile("[0-9]{4}");
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()){
            return matcher.group();
        }
        return "";
    }

    public static String matchSeries(String query){
        Pattern pattern = Pattern.compile("第.?[部,季,期]");
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()){
            return matcher.group();
        }
        return "";
    }
    public static String matchResource(String query){
        String reg = String.format("[%s]", StringUtils.join(resourceListCN, ","));
//        System.out.println(reg);
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(query);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()){
            stringBuffer.append(matcher.group());
        }
        return stringBuffer.toString();
    }
    public static String matchPay(String query){
        Pattern pattern = Pattern.compile("[免费, 免费观看]");
        Matcher matcher = pattern.matcher(query);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()){
            stringBuffer.append(matcher.group());
        }
        return stringBuffer.toString();
    }

    /**
     * 祛除杂质
     * @param query
     * @return
     */
    public static String delImpurity(String query){
        for(String impurity: ImpurityArr){
            query = query.trim().replace("", "");
        }
        return query;
    }
    public static void main(String[] args) {
        String query = "天龙八部1987第一季腾讯";
        RegUtils regUtils = new RegUtils();
        System.out.println(matchResource(query));

    }
}
