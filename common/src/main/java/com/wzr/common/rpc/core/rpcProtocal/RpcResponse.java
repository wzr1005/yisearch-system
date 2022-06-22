package com.wzr.yi.rpc.core.rpcProtocal;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 12:33 上午
 * 可序列化的返回体
 */
@Data
@Builder
public class RpcResponse implements Serializable {
    // 协议头部分
    private String header;
    // 协议体部分
    private byte[] body;
}
