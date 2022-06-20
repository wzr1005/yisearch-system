package com.wzr.yi.service;

import com.wzr.yi.bean.EsRequestBody;

import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/6/9 11:05 下午
 * 对传入对request进行意图分析、拆解
 */
public interface SearchPrepareService {

    public List<EsRequestBody> QUAnalysis(EsRequestBody esRequestBody);

}
