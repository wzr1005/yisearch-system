//package com.wzr.yi.rpc.Test.client;
//
//import com.wzr.yi.rpc.IDL.HelloRequest;
//import com.wzr.yi.rpc.IDL.HelloResponse;
//import com.wzr.yi.rpc.IDL.HelloService;
//import com.wzr.yi.rpc.core.client.RpcClientProxy;
//
///**
// * @autor zhenrenwu
// * @date 2022/6/12 2:06 下午
// */
//public class TestClient {
//    public static void main(String[] args) {
//        // 获取RpcService
//        RpcClientProxy proxy = new RpcClientProxy();
//        HelloService helloService = proxy.getService(HelloService.class);
//        System.out.println("获取service代理");
//        // 构造出请求对象HelloRequest
//        HelloRequest helloRequest = new HelloRequest("wzr");
//        // rpc调用并返回结果对象HelloResponse
//        System.out.println("准备发送");
//        HelloResponse helloResponse = helloService.hello(helloRequest);
//        // 从HelloResponse中获取msg
//        String helloMsg = helloResponse.getMsg();
//
//        System.out.println(helloMsg);
//    }
//
//}
