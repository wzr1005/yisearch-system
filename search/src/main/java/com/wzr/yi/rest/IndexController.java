package com.wzr.yi.rest;

import com.wzr.yi.common.searchEntity.EsRequestBody;
import com.wzr.yi.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @autor zhenrenwu
 * @date 2022/2/20 2:53 下午
 */
@RestController
@EnableAutoConfiguration
@RequiredArgsConstructor
@RequestMapping("/es")
public class IndexController {

    private final IndexService indexService;
//    @RequestMapping("/getIndices")
//    @ResponseBody
//    public ResponseEntity getIndices(@RequestBody EsrequestBody esrequestBody) throws ExecutionException, InterruptedException {
//
//    }

//    @RequestMapping("/redis")
//    public boolean getRedis(EsRequestBody esRequestBody){
//        return indexService.redis();
//    }

    @RequestMapping("/createIndex")
    public ResponseEntity createIndex(@RequestBody EsRequestBody esRequestBody) throws ExecutionException, InterruptedException {
        return indexService.createIndex(esRequestBody);
    }


//    @GetMapping("/getData")
//    @ResponseBody
//    public String getData(@RequestBody EsrequestBody esrequestBody){
//        if(StringUtils.isNotBlank(esrequestBody.getId())) {
//        Map<String, Object> map= ElasticsearchUtil.searchDataById(esrequestBody.getIndexName(),esrequestBody.getType(),esrequestBody.getId(),esrequestBody.getFields());
//        GetRequestBuilder getRequestBuilder = transportClient.prepareGet(esrequestBody.getIndexName(), esrequestBody.getType(), esrequestBody.getId());
//
//        SearchResponse searchResponse = transportClient.prepareSearch(esrequestBody.getIndexName()).setTypes(esrequestBody.getType()).setQuery(QueryBuilders
//                .matchAllQuery()).setFetchSource("*","age").get();
//
//        SearchHits map = searchResponse.getHits();
//        return JSONObject.toJSONString(map);
//    }

//    @PostMapping("/createIndex")
//    public Boolean createIndex(@RequestBody EsrequestBody esrequestBody){
//        String indexName = esrequestBody.getIndexName();
//        if(!ElasticsearchUtil.isIndexExist(indexName)){
//            return false;
//        }
//
//    }

//    @GetMapping("/indices")
//    public ResponseEntity getIndex(){
//        SearchResponse searchResponse = transportClient.prepareSearch("dangdang")
//                .setQuery(new MatchAllQueryBuilder())
//                .get();
//        searchResponse = elasticsearchUtil.
//        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
//    }
//
//    @PostMapping("createIndex")
//    public ResponseEntity testCreateIndex(){
//        //创建一个索引名字为dangdang
//        CreateIndexResponse createIndexResponse = transportClient.admin().indices().prepareCreate("dangdang").get();
//        //获取返回信息
//        return new ResponseEntity<>(createIndexResponse, HttpStatus.OK);
//    }
//
//    @PutMapping("updateIndex")
//    public void updateIndex() throws ExecutionException, InterruptedException {
//        CreateIndexRequest dangdangIndex = new CreateIndexRequest("dangdang");
//        //String type, String source, XContentType xContentType
//        String property = "{\"properties\":{\"actors\":{\"type\":\"text\",\"analyzer\":\"ik_smart\"},\"hypernym\":{\"type\":\"text\",\"analyzer\":\"ik_smart\"},\"video_type\":{\"type\":\"text\",\"analyzer\":\"ik_smart\"},\"year\":{\"type\":\"integer\"},\"bangdan\":{\"type\":\"text\",\"analyzer\":\"ik_smart\"},\"video_intro\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\"},\"lang\":{\"type\":\"text\",\"analyzer\":\"ik_smart\"},\"latest_issue_time\":{\"type\":\"keyword\"},\"name\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\"}}}";
//        dangdangIndex.mapping("bangdan",property, XContentType.JSON);
//        CreateIndexResponse createIndexResponse = transportClient.admin().indices().create(dangdangIndex).get();
//        System.out.println(createIndexResponse.isAcknowledged());
//    }
//    @DeleteMapping("delIndex")
//    public void delIndex(){
//        AcknowledgedResponse acknowledgedResponse =transportClient.admin().indices().prepareDelete("dangdang").get();
//        System.out.println(acknowledgedResponse.isAcknowledged());
//    }
//    @PostMapping("bukInsert")
//    public void bulkInsert(String s){
//
//    }

}
