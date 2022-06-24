package com.wzr.yi.service;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 12:32 下午
 */
public interface SourceDataService {

    //将爬取或cp方来的数据，增量更新到es数据库中
    public boolean fullDataUpdate();

    // 增量更新，根据eid
    public boolean incrementalUpdate();
}
