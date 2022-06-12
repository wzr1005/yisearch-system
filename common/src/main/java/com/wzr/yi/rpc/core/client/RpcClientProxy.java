package com.wzr.yi.rpc.core.client;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.wzr.yi.rpc.core.codec.RpcRequestBody;
import com.wzr.yi.rpc.core.codec.RpcResponseBody;
import com.wzr.yi.rpc.core.rpcProtocal.RpcRequest;
import com.wzr.yi.rpc.core.rpcProtocal.RpcResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 12:38 上午
 */
public class RpcClientProxy implements InvocationHandler {

    public <T> T getService(Class<T> clazz){
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 1. 将调用所需信息编码成bytes[], 即有了调用编码 [codec层]
        //request字节流
        System.out.println("调用service代理");
        RpcRequestBody rpcRequestBody = RpcRequestBody.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .paramTypes(method.getParameterTypes())
                .parameters(args)
                .build();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(rpcRequestBody);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println("获取request字节流");
        // 2. 创建RPC协议， 将header、body的内容设置好，Body中存放调用编码 (protocol层)
        RpcRequest rpcRequest = RpcRequest.builder()
                .header("version=1")
                .body(bytes)
                .build();
        System.out.println("创建rpcRequest协议,sendRequest");
        // 3. 发送RpcRequest，获得RpcResponse 阻塞发送
        RpcClientTransfer rpcClient = new RpcClientTransfer();
        RpcResponse rpcResponse = rpcClient.sendRequest(rpcRequest);

        // 4. 解析RpcResponse，也就是在解析rpc协议[protocol]层
        System.out.println("解析RpcResponse");
        String header = rpcResponse.getHeader();
        byte[] body = rpcResponse.getBody();
        if(header.equals("version=1")){
            //将PrcResponse的body中的返回编码，解码成我们需要的对象Object并返回codec层
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            RpcResponseBody rpcResponseBody = (RpcResponseBody) objectInputStream.readObject();
            Object retObject = rpcResponseBody.getRetObject();
            System.out.println("解析完成，返回retObject");
            return retObject;
        }
        return null;
    }
}
