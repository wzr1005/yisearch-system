package com.wzr.yi.service;

import com.wzr.yi.bean.EsRequestBody;
import com.wzr.yi.entity.IndexPropertyDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @autor zhenrenwu
 * @date 2022/2/22 9:54 下午
 */
public interface IndexService {

    public ResponseEntity createIndex();

    ResponseEntity createIndex(EsRequestBody esRequestBody) throws ExecutionException, InterruptedException;

//    public boolean redis();

    public boolean BulkInsertMysql(List<IndexPropertyDto> objList);

    public String Testmybatis();
    public List<Map<String, Object>> testDruid();

    public List<Map<String, Object>> sortResult(List<Map<String, Object>> result);
}
