package com.wzr.yi.service.impl;

import com.wzr.yi.service.SearchPrepareService;
import com.wzr.yi.util.RegUtils;
import com.wzr.yi.common.searchEntity.EsRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/6/9 11:10 下午
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SearchPrepareServiceImpl implements SearchPrepareService {
    @Override
    public List<EsRequestBody> QUAnalysis(EsRequestBody requestBody) {
        String query = requestBody.getQuery();
         /*
         QU逻辑，画图
         1。如果query中含有空格，切分，
         2。如果query中含有数字，截取
            1。若属于1900-2022，注入到year
            2。若属于1-20， 注入到series中
         3。如果query中含有[电影，电视剧，卡通/动漫，综艺]，将相应的品类注入到type
         4。如果query中含有演员(从词表中查），如果命中即新增一次演员search
         entity_index starring定时遍历->写入到database（暂定redis）中
         5。导演同理
         6。如果query中含有资源cp方，对召回排序产生影响，用于召回排序，注入到resource中
         7。如果query中含有杂质，则去除，例如，在线观看，
         8。如果query中含有免费观看，则对召回排序产生影响，注入到pay字段中
         9。如果query中含有演员表，则index为非影视类，
          */
        List<EsRequestBody> esRequestBodyList = new ArrayList<>();
        /* 先对query进行提纯 */
        query = RegUtils.delImpurity(query);
        if(query.contains(" ")){
            // query拆解
            String[] queryArr = query.split(" ");
            for(String q: queryArr){
                EsRequestBody requestBodyTemp = new EsRequestBody(requestBody);
                String ac = null;

                // query中含有年份
                if((ac = RegUtils.matchYear(q)) != ""){
                    requestBody.setYear(ac);
                    q = q.replace(ac, "");
                }
                // query中含有系列部
                if((ac = RegUtils.matchSeries(query)) != ""){
                    requestBody.setSeries(ac);
                    q = q.replace(ac, "");
                }
                // query中含有资源
                if((ac = RegUtils.matchResource(query)) != ""){
                    requestBody.setResource(ac);
                    q = q.replace(ac, "");
                }
                // query中含有免费
                if((ac = RegUtils.matchPay(query)) != ""){
                    requestBody.setPay("1");
                    q = q.replace(ac, "");
                }
                esRequestBodyList.add(requestBodyTemp);
            }
        }

//        QueryBuilder queryBuilder = new MultiMatchQueryBuilder(requestBody.getQuery(), requestBody.getFields());
//        queryBuilder.boost(8.0F);
//
//        QueryBuilder queryBuilders = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("query", "value"));
        return esRequestBodyList;
    }

    public static void main(String[] args) {
        EsRequestBody esRequestBody = new EsRequestBody();
        esRequestBody.setQuery("天龙八部1997");
    }
}
