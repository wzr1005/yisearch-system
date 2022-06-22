package com.wzr.common.rpc.core.codec;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 12:13 上午
 * 将request转为Java可以处理的格式
 */
@Data
@Builder
public class RpcRequestBody implements Serializable {
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
}
