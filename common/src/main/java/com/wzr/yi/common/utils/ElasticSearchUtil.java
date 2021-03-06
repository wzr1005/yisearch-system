package com.wzr.yi.common.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzr.yi.common.exception.BadRequestException;
import com.wzr.yi.common.searchEntity.EsRequestBody;
import com.wzr.yi.common.searchEntity.Dto.EsPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
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

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.wzr.yi.common.constant.Constant.BANGDANMAP;
/**
 * @autor zhenrenwu
 * @date 2022/2/20 3:28 ??????
 */
@Slf4j
@Component
public class ElasticSearchUtil {

    private String client;

    private TransportClient transportClient; //?????????????????????bean???????????????new????????????

    public ElasticSearchUtil(TransportClient transportClient) {
        this.transportClient = transportClient;
    }


    public <T> List<Map<String, Object>> queryHot(EsRequestBody requestBody){
        SearchRequest searchRequest = new SearchRequest(requestBody.getIndexName());
        searchRequest.types(requestBody.getType());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.sort("hotCount", SortOrder.DESC);
        searchSourceBuilder.size(50);
        return getMapList(searchRequest, searchSourceBuilder);
    }

    private List<Map<String, Object>> getMapList(SearchRequest searchRequest, SearchSourceBuilder searchSourceBuilder) {
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = transportClient.search(searchRequest).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return processResponse(searchResponse);
    }

    public List<Map<String, Object>> queryAll(EsRequestBody requestBody){
        SearchRequest searchRequest = new SearchRequest(requestBody.getIndexName());
        searchRequest.types(requestBody.getType());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        return getMapList(searchRequest, searchSourceBuilder);

    }
    
    public List<Map<String, Object>> queryBangdan(EsRequestBody requestBody){

        SearchRequest searchRequest = new SearchRequest(requestBody.getIndexName());
        searchRequest.types("_doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort("order");
        searchSourceBuilder.query(QueryBuilders.matchQuery("bangdan", BANGDANMAP.get(requestBody.getCategory())));

        return getMapList(searchRequest, searchSourceBuilder);
    }
    public List<Map<String, Object>> queryEs(EsRequestBody requestBody){
        // es Java?????????????????????
        //??????????????????

        SearchRequest searchRequest = new SearchRequest(requestBody.getIndexName());
        searchRequest.types(requestBody.getType());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //????????????
        //MultiMatchQuery

        QueryBuilder queryBuilder = new MultiMatchQueryBuilder(requestBody.getQuery(), requestBody.getFields());
//        QueryBuilders.boolQuery()
//                        .must()
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(30);
//        SearchResponse searchResponse = transportClient
//                .prepareSearch(requestBody.getIndexName())
//                .setQuery(queryBuilder)  //??????????????????
//                .get();
        return getMapList(searchRequest, searchSourceBuilder);
    }
    public List<Map<String, Object>> processResponse(SearchResponse searchResponse){
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            Map<String, Object> mpObject = (Map<String, Object>) JSONObject.parse(sourceAsString);
            list.add(mpObject);
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
     * ????????????????????????
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


    public CreateIndexResponse createIndex(EsRequestBody esRequestBody) throws ExecutionException, InterruptedException {
        String indexName = esRequestBody.getIndexName();
        if(indexName == null){
            throw new BadRequestException("????????????????????????");
        }
        CreateIndexRequest index = new CreateIndexRequest(indexName);
        //??????json????????????
        if(esRequestBody.getJson()!=null){
            index.mapping(indexName, esRequestBody.getJson(), XContentType.JSON);
        }
        return transportClient.admin().indices().create(index).get();
    }
    /**
     * ?????????????????????ID
     *
     * @param jsonObject ??????????????????
     * @param index      ????????????????????????
     * @param type       ??????????????????
     * @param id         ??????ID
     * @return
     */
    public String addData(JSONObject jsonObject, String index, String type, String id) {
        IndexResponse response = transportClient.prepareIndex(index, type, id).setSource(jsonObject).get();
        log.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }
    /**
     * ????????????
     *
     * @param jsonObject ??????????????????
     * @param index      ????????????????????????
     * @param type       ??????????????????
     * @return
     */
    public  String addData(JSONObject jsonObject, String index, String type) {
        return addData(jsonObject, index, type, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    /**
     * ??????ID????????????
     *
     * @param index ????????????????????????
     * @param type  ??????????????????
     * @param id    ??????ID
     */
    public  void deleteDataById(String index, String type, String id) {
        DeleteResponse response = transportClient.prepareDelete(index, type, id).execute().actionGet();
        log.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());
    }

    /**
     * ??????ID ????????????
     *
     * @param jsonObject ??????????????????
     * @param index      ????????????????????????
     * @param type       ??????????????????
     * @param id         ??????ID
     * @return
     */
    public  void updateDataById(JSONObject jsonObject, String index, String type, String id) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index).type(type).id(id).doc(jsonObject);
        transportClient.update(updateRequest);
    }

    public void BulkInsertDocument(String index, String type, List<String> list){
        /*new ??????bulkProcessor*/
//        transportClient.in
        long beginTime = System.currentTimeMillis();
        BulkProcessor bulkProcessor = BulkProcessor.builder(transportClient, new BulkProcessor.Listener() {
            private long beginTime;
            @Override
            //todo beforeBulk??????????????????????????????
            public void beforeBulk(long executionId, BulkRequest bulkRequest) {
                this.beginTime = System.currentTimeMillis();
                log.info("??????????????????es" + bulkRequest.numberOfActions() + "?????????---");
            }

            @Override
            //TODO ?????????afterBulk???????????????????????????????????????beforeBulk??????????????????????????????
            public void afterBulk(long executionId, BulkRequest bulkRequest, BulkResponse response) {
                long detTime = System.currentTimeMillis() - beginTime;
                log.info("??????????????????es???" + bulkRequest.numberOfActions() + String.format("?????????---, cost %d ms", detTime));
            }

            @Override
            public void afterBulk(long executionId, BulkRequest bulkRequest, Throwable failure) {
                log.info("????????????es??????" + bulkRequest.numberOfActions() + "?????????---");
                log.error(failure.getMessage());

            }
        })
                // 1w?????????????????????bulk
                .setBulkActions(10000)
                // 1gb?????????????????????bulk
                .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                // ??????5s??????????????????
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                // ??????????????????, 0?????????, 1??????????????????
                .setConcurrentRequests(1)
                // ????????????, 100ms?????????, ????????????3???
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        // ??????????????????
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
        ThreadPoolUtils.isCompleted(threadPoolExecutor);

        bulkProcessor.flush();
        log.info(String.format("????????? %d", System.currentTimeMillis()-beginTime));
    }


    /**
     *
     * @param
     * @return
     */
    public  Map<String,Object> queryById(String index, String id) {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.types("_doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("id", id));

        return getMapList(searchRequest, searchSourceBuilder).get(0);

    }

    /**
     * ??????????????????,?????????
     *
     * @param index          ????????????
     * @param type           ????????????,???????????????type????????????
     * @param startPage    ?????????
     * @param pageSize       ??????????????????
     * @param query          ????????????
     * @param fields         ???????????????????????????????????????????????????????????????
     * @param sortField      ????????????
     * @param highlightField ????????????
     * @return
     */
    public EsPage searchDataPage(String index, String type, int startPage, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);

        // ???????????????????????????????????????????????????????????????
        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }

        //????????????
        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        // ?????????xxx=111,aaa=222???
        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            //highlightBuilder.preTags("<span style='color:red' >");//????????????
            //highlightBuilder.postTags("</span>");//????????????

            // ??????????????????
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        //searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        searchRequestBuilder.setQuery(query);

        // ????????????
        searchRequestBuilder.setFrom(startPage).setSize(pageSize);

        // ????????????????????????????????????
        searchRequestBuilder.setExplain(true);

        //??????????????? ????????? Elasticsearch head ??? Kibana  ???????????????
        log.info("\n{}", searchRequestBuilder);

        // ????????????,????????????????????????
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        log.debug("????????????[{}]?????????,??????????????????[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            // ????????????
            List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);

            return new EsPage(startPage, pageSize, (int) totalHits, sourceList);
        }

        return null;

    }

    /**
     * ??????????????????
     *
     * @param index          ????????????
     * @param type           ????????????,???????????????type????????????
     * @param query          ????????????
     * @param size           ??????????????????
     * @param fields         ???????????????????????????????????????????????????????????????
     * @param sortField      ????????????
     * @param highlightField ????????????
     * @return
     */
    public  List<Map<String, Object>> searchListData(String index, String type, QueryBuilder query, Integer size, String fields, String sortField, String highlightField) {

        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }

        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // ??????????????????
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

        //??????????????? ????????? Elasticsearch head ??? Kibana  ???????????????
        log.info("\n{}", searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;

        log.info("????????????[{}]?????????,??????????????????[{}]", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            // ????????????
            return setSearchResponse(searchResponse, highlightField);
        }

        return null;

    }


    /**
     * ??????????????? ????????????
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

                System.out.println("?????? ???????????????????????? ???????????????" + searchHit.getSourceAsMap());
                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();

                if (text != null) {
                    for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
                    //?????? ???????????????????????? ???????????????
                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }

        return sourceList;
    }
}
