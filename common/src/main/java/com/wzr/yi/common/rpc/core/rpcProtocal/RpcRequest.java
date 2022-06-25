package com.wzr.yi.common.rpc.core.rpcProtocal;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 12:31 上午
 * 可序列化的request
 */
@Data
@Builder
// Serializable : 将对象变成可传输的字节序列
public class RpcRequest implements Serializable {
    // 协议头部分
    private String header;
    // 协议体部分
    private byte[] body;
}
