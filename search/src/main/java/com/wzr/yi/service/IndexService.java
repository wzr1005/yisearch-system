package com.wzr.yi.service;

import com.wzr.yi.bean.EsRequetBody;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

/**
 * @autor zhenrenwu
 * @date 2022/2/22 9:54 下午
 */
public interface IndexService {

    public ResponseEntity createIndex();

    ResponseEntity createIndex(EsRequetBody esRequetBody) throws ExecutionException, InterruptedException;

    public boolean redis();
}
