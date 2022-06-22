package com.wzr.common.rpc.IDL;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 12:10 上午
 */
@Data
@AllArgsConstructor
public class HelloResponse implements Serializable {
    private String msg;

}
