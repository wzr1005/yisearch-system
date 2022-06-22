package com.wzr.common.rpc.core.codec;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 12:14 上午
 * Java接收用于返回体，用于编码
 */
@Data
@Builder
public class RpcResponseBody implements Serializable {
    private Object retObject;

}
