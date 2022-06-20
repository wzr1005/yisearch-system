package com.wzr.yi.service.impl;

import com.wzr.yi.bean.EsRequestBody;
import com.wzr.yi.service.SearchPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pythonService.PythonServiceClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/10 4:04 下午
 *
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SearchPostServiceImpl implements SearchPostService {
    @Override
    public List<Map<String, Object>> resultSort(List<Map<String, Object>> list, EsRequestBody esRequestBody) {
        // 排序策略
        // 1。计算query与召回实体之间的编辑距离，若高度相似，可加1-10分
        // 2。命中别名，加3分
        // 2。若query中含有季部，则season匹配上可以再加5分，若无，则按season逆序排序
        // 3。若query中含有年份，则匹配上可以加5分
        // 4。若query中含有资源cp，则一次加3分
        // 4。若describe中有match上的，
        // 5。类型召回？
        Map<String, Integer> scoreMap = new HashMap<>();
        PythonServiceClient pythonServiceClient = new PythonServiceClient("localhost", 50000);
        list.forEach(result->{
            String id = (String) result.get("id");
            Integer score = (Integer) result.getOrDefault(id, 0);
            String entityName = (String) result.get("name");
            String query = (String) result.get("query");
            String[] alias = ((String) result.get("query")).split(";");

            Float dist = pythonServiceClient.EditDistServer(entityName, query);
            //            query与召回实体之间
            if(dist > 0.8){
                score += (Integer) Math.round(dist*10);
            }else {
                //            query与别名
                for(String alia : alias){
                    if(pythonServiceClient.EditDistServer(entityName, query) > 0.8){
                        score += (Integer) Math.round(dist*10);
                        break;
                    }
                }
            }




        });
        return null;
    }

}
