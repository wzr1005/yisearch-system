package com.wzr.yi.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzr.yi.bean.EsRequetBody;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.exception.BadRequestException;
import com.wzr.yi.util.Dto.EsPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.wzr.yi.util.ThreadPoolUtils.isCompleted;

/**
 * @autor zhenrenwu
 * @date 2022/2/20 3:28 下午
 */
@Slf4j
@Component
public class ElasticSearchUtil {

    private String client;

    private TransportClient transportClient; //这个是被注入了bean的，相当于new了个对象

    public ElasticSearchUtil(TransportClient transportClient) {
        this.transportClient = transportClient;
    }




    public List<Map<String, Object>> queryEs(EsRequetBody requetBody){
        // es Java客户端查询案例
        //搜索请求对象

        SearchRequest searchRequest = new SearchRequest(requetBody.getIndexName());
        searchRequest.types(requetBody.getType());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索方式
        //MultiMatchQuery

        QueryBuilder queryBuilder = new MultiMatchQueryBuilder(requetBody.getQuery(), requetBody.getFields());
//        QueryBuilders.boolQuery()
//                        .must()
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(30);
//        SearchResponse searchResponse = transportClient
//                .prepareSearch(requetBody.getIndexName())
//                .setQuery(queryBuilder)  //设置查询方式
//                .get();
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = transportClient.search(searchRequest).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = (JSONObject) JSONObject.parse(sourceAsString);
            Field[] fields = IndexPropertyDto.class.getDeclaredFields();
            Map<String, Object> mp = new HashMap<>();
            for(Field field: fields){
                try {
                    String key = field.getName();
                    String value = MyStringUtils.getValueOrDefault(jsonObject.get(key));
                    mp.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            list.add(mp);
        }

        return list;
    }

    public boolean DeleteDoc(String index, String type, List<String> deleteList){
        deleteList.forEach(id->{
            DeleteResponse deleteResponse = transportClient.prepareDelete().setId(id).get();
            deleteResponse.status();
        });
        return true;
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public boolean isIndexExist(String index) {
        System.out.println(transportClient.prepareSearch("dangdang")
                .setQuery(new MatchAllQueryBuilder())
                .get());
        IndicesExistsResponse inExistsResponse = transportClient.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()) {
            log.info("Index [" + index + "] is exist!");
        } else {
            log.info("Index [" + index + "] is not exist!");
        }
        return inExistsResponse.isExists();
    }


    public CreateIndexResponse createIndex(EsRequetBody esRequetBody) throws ExecutionException, InterruptedException {
        String indexName = esRequetBody.getIndexName();
        if(indexName == null){
            throw new BadRequestException("索引名字不能为空");
        }
        CreateIndexRequest index = new CreateIndexRequest(indexName);
        //定义json格式映射
        if(esRequetBody.getJson()!=null){
            index.mapping(indexName,esRequetBody.getJson(), XContentType.JSON);
        }
        return transportClient.admin().indices().create(index).get();
    }
    /**
     * 数据添加，正定ID
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public String addData(JSONObject jsonObject, String index, String type, String id) {
        IndexResponse response = transportClient.prepareIndex(index, type, id).setSource(jsonObject).get();
        log.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }
    /**
     * 数据添加
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @return
     */
    public  String addData(JSONObject jsonObject, String index, String type) {
        return addData(jsonObject, index, type, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    /**
     * 通过ID删除数据
     *
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     */
    public  void deleteDataById(String index, String type, String id) {
        DeleteResponse response = transportClient.prepareDelete(index, type, id).execute().actionGet();
        log.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public  void updateDataById(JSONObject jsonObject, String index, String type, String id) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index).type(type).id(id).doc(jsonObject);
        transportClient.update(updateRequest);
    }

    public void BulkInsertDocument(String index, String type, List<String> list){
        /*new 一个bulkProcessor*/
//        transportClient.in
        long beginTime = System.currentTimeMillis();
        BulkProcessor bulkProcessor = BulkProcessor.builder(transportClient, new BulkProcessor.Listener() {
            private long beginTime;
            @Override
            //todo beforeBulk会在批量提交之前执行
            public void beforeBulk(long executionId, BulkRequest bulkRequest) {
                this.beginTime = System.currentTimeMillis();
                log.info("开始批量插入es" + bulkRequest.numberOfActions() + "条数据---");
            }

            @Override
            //TODO 第一个afterBulk会在批量成功后执行，可以跟beforeBulk配合计算批量所需时间
            public void afterBulk(long executionId, BulkRequest bulkRequest, BulkResponse response) {
                long detTime = System.currentTimeMillis() - beginTime;
                log.info("完成批量插入es，" + bulkRequest.numberOfActions() + String.format("条数据---, cost %d ms", detTime));
            }

            @Override
            public void afterBulk(long executionId, BulkRequest bulkRequest, Throwable failure) {
                log.info("批量插入es失败" + bulkRequest.numberOfActions() + "条数据---");
                log.error(failure.getMessage());

            }
        })
                // 1w次请求执行一次bulk
                .setBulkActions(10000)
                // 1gb的数据刷新一次bulk
                .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                // 固定5s必须刷新一次
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                // 并发请求数量, 0不并发, 1并发允许执行
                .setConcurrentRequests(1)
                // 设置退避, 100ms后执行, 最大请求3次
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        // 添加请求数据
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100000),
                r ->{return new Thread(r);},
                (r,executor)->{if(!executor.isShutdown()){
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }}});
        list.forEach(entity->{
            try{
                JSONObject jsonObject = JSON.parseObject(entity);
                String eid = jsonObject.getString("eid");
                threadPoolExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        bulkProcessor.add(new IndexRequest(index).type(type).id(eid).source(entity, XContentType.JSON));
                    }
                });
            }catch (Exception e){
                log.error(e.getMessage());
            }
        });
        isCompleted(threadPoolExecutor);

        bulkProcessor.flush();
        log.info(String.format("总耗时 %d", System.currentTimeMillis()-beginTime));
    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */


    public  Map<String,Object> searchDataById(String index, String type, String id, String fields) {
        GetRequestBuilder getRequestBuilder = transportClient.prepareGet(index, type, id);
        if (StringUtils.isNotEmpty(fields)) {
            getRequestBuilder.setFetchSource(fields.split(","), null);
        }
        GetResponse getResponse = getRequestBuilder.execute().actionGet();
        return getResponse.getSource();
    }

    /**
     * 使用分词查询,并分页
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param startPage    当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public  EsPage searchDataPage(String index, String type, int startPage, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);

        // 需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }

        //排序字段
        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        // 高亮（xxx=111,aaa=222）
        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            //highlightBuilder.preTags("<span style='color:red' >");//设置前缀
            //highlightBuilder.postTags("</span>");//设置后缀

            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        //searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        searchRequestBuilder.setQuery(query);

        // 分页应用
        searchRequestBuilder.setFrom(startPage).setSize(pageSize);

        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        log.info("\n{}", searchRequestBuilder);

        // 执行搜索,返回搜索响应信息
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        log.debug("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);

            return new EsPage(startPage, pageSize, (int) totalHits, sourceList);
        }

        return null;

    }

    /**
     * 使用分词查询
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param query          查询条件
     * @param size           文档大小限制
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public  List<Map<String, Object>> searchListData(String index, String type, QueryBuilder query, Integer size, String fields, String sortField, String highlightField) {

        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }

        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        searchRequestBuilder.setQuery(query);

        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }
        searchRequestBuilder.setFetchSource(true);

        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        if (size != null && size > 0) {
            searchRequestBuilder.setSize(size);
        }

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        log.info("\n{}", searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        log.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            return setSearchResponse(searchResponse, highlightField);
        }

        return null;

    }


    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     * @param highlightField
     */
    private  List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
        StringBuffer stringBuffer = new StringBuffer();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());

            if (StringUtils.isNotEmpty(highlightField)) {

                System.out.println("遍历 高亮结果集，覆盖 正常结果集" + searchHit.getSourceAsMap());
                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();

                if (text != null) {
                    for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
                    //遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }

        return sourceList;
    }
}
