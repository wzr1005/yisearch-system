package com.wzr.yi.service.Impl;

import com.wzr.yi.service.SourceDataService;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 12:41 下午
 */
public class SourceDataServiceImpl implements SourceDataService {
    @Override
    public boolean fullDataUpdate() {
        return false;
    }

    @Override
    public boolean incrementalUpdate() {
        return false;
    }
}
