package com.wzr.yi.rpc.Test.server;

import com.wzr.yi.rpc.IDL.HelloRequest;
import com.wzr.yi.rpc.IDL.HelloResponse;
import com.wzr.yi.rpc.IDL.HelloService;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 2:11 下午
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public HelloResponse hello(HelloRequest helloRequest) {
        System.out.println("调用服务方法");
        String name = helloRequest.getName();
        String retMsg = "hello: " + name;
        HelloResponse response = new HelloResponse(retMsg);
        System.out.println("服务完成，返回结果");
        return response;
    }
}
