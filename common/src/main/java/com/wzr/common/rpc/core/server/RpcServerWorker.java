package com.wzr.yi.rpc.core.server;

import com.wzr.yi.rpc.core.codec.RpcRequestBody;
import com.wzr.yi.rpc.core.codec.RpcResponseBody;
import com.wzr.yi.rpc.core.rpcProtocal.RpcRequest;
import com.wzr.yi.rpc.core.rpcProtocal.RpcResponse;

import javax.imageio.spi.RegisterableService;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 1:08 下午
 */
public class RpcServerWorker implements  Runnable{
    private Socket socket;
    private HashMap<String, Object> registeredService;

    public RpcServerWorker(Socket socket, HashMap<String, Object> registeredService) {
        this.socket = socket;
        this.registeredService = registeredService;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(String.format("线程池开启线程%s", Thread.currentThread().getName()));
            // 1. transfer 层获取到RpcRequest消息[transfer层]
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();

            // 2. 解析版本号，并判断[protocol层]
            System.out.println("解析版本号");
            if(rpcRequest.getHeader().equals("version=1")){
                // 3. 将rpcRequest中的body部分编码解码出来变成RpcRequestBody[codec层] ->字节流 -> requestBody
                byte[] body = rpcRequest.getBody();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
                ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
                RpcRequestBody rpcRequestBody = (RpcRequestBody) inputStream.readObject();

                // 调用服务 requestBody -> 拿到方法名，参数方法 -> invoke调用执行 -> 得到未经序列化的返回内容
                System.out.println(String.format("调用服务%s", rpcRequestBody.getMethodName()));
                Object service = registeredService.get(rpcRequestBody.getInterfaceName());
                Method method = service.getClass().getMethod(rpcRequestBody.getMethodName(), rpcRequestBody.getParamTypes());
                Object returnObject = method.invoke(service, rpcRequestBody.getParameters());

                // 1. 将returnObject编码成bytes[]即变成了返回编码[codec层]
                System.out.println("服务结束，返回response");
                RpcResponseBody rpcResponseBody = RpcResponseBody.builder()
                        .retObject(returnObject).build();

                // 1. 将returnObject编码成bytes[]即变成了返回编码 [codec]
                System.out.println("将服务返回的结果编码成字节流，byteArrayOutputStream");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(rpcResponseBody);
                byte[] bytes = baos.toByteArray();

                // 2. 将返回编码作为body，加上header，生成RpcResponse协议 [protocol层]
                System.out.println("将编码注入RpcResponse，Java支持的对象");
                RpcResponse rpcResponse = RpcResponse.builder()
                        .header("version=1")
                        .body(bytes)
                        .build();

                // 3. 发送[transfer层]
                System.out.println("发送到client的上层，等待读取");
                objectOutputStream.writeObject(rpcResponse);
                objectOutputStream.flush();

            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
