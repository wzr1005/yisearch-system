package com.wzr.yi.service.impl;
/**
 * @autor zhenrenwu
 * @date 2022/6/4 12:38 下午
 */
import com.wzr.yi.bean.EsRequetBody;
import com.wzr.yi.service.IndexService;
import com.wzr.yi.service.SearchPrepareService;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(Parameterized.class)
public class TestQu {
    @Test
    public void testQu() {
        SearchPrepareService searchPrepareService = new SearchPrepareServiceImpl();
        String query = "天龙八部1987";
        EsRequetBody esRequetBody = new EsRequetBody();
        esRequetBody.setQuery(query);
        searchPrepareService.QUAnalysis(esRequetBody);
    }

    public void testMatchYear(){
        String query = "天龙八部1987";

        Pattern pattern = Pattern.compile("[0-9]{4}");
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()){
            System.out.println(matcher.group());
        }

    }
    public void testMatchSeries(){
        String query = "天龙八部1987第一季";
        Pattern pattern = Pattern.compile("第.?[部,季]");
        Matcher matcher = pattern.matcher(query);
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }
    public static void main(String[] args) {
        TestQu testQu = new TestQu();
        testQu.testMatchYear();
        testQu.testMatchSeries();
    }

}
