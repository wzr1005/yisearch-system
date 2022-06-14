package com.wzr.yi.service.impl;

import com.wzr.yi.bean.EsRequetBody;
import com.wzr.yi.service.SearchPrepareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wzr.yi.util.RegUtils.*;

/**
 * @autor zhenrenwu
 * @date 2022/6/9 11:10 下午
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SearchPrepareServiceImpl implements SearchPrepareService {
    @Override
    public List<EsRequetBody> QUAnalysis(EsRequetBody requetBody) {
        String query = requetBody.getQuery();
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
        List<EsRequetBody> esRequetBodyList = new ArrayList<>();
        /* 先对query进行提纯 */
        query = delImpurity(query);
        if(query.contains(" ")){
            // query拆解
            String[] queryArr = query.split(" ");
            for(String q: queryArr){
                EsRequetBody requetBodyTemp = new EsRequetBody(requetBody);
                String ac = null;

                // query中含有年份
                if((ac = matchYear(q)) != ""){
                    requetBody.setYear(ac);
                    q = q.replace(ac, "");
                }
                // query中含有系列部
                if((ac = matchSeries(query)) != ""){
                    requetBody.setSeries(ac);
                    q = q.replace(ac, "");
                }
                // query中含有资源
                if((ac = matchResource(query)) != ""){
                    requetBody.setResource(ac);
                    q = q.replace(ac, "");
                }
                // query中含有免费
                if((ac = matchPay(query)) != ""){
                    requetBody.setPay("1");
                    q = q.replace(ac, "");
                }
                esRequetBodyList.add(requetBodyTemp);
            }
        }

//        QueryBuilder queryBuilder = new MultiMatchQueryBuilder(requetBody.getQuery(), requetBody.getFields());
//        queryBuilder.boost(8.0F);
//
//        QueryBuilder queryBuilders = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("query", "value"));
        return esRequetBodyList;
    }

    public static void main(String[] args) {
        EsRequetBody esRequetBody = new EsRequetBody();
        esRequetBody.setQuery("天龙八部1997");
    }
}
