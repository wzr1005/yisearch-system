package com.wzr.yi.common.rpc.core.client;

import com.wzr.yi.common.rpc.core.rpcProtocal.RpcRequest;
import com.wzr.yi.common.rpc.core.rpcProtocal.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 1:16 上午
 * 传入protocol层的RpcRequest，输出protocol层的RpcResponse
 */
public class RpcClientTransfer {
    public RpcResponse sendRequest(RpcRequest rpcRequest) {
        try(Socket socket = new Socket("127.0.0.1", 9001)){
            // 发送transfer层
            System.out.println("发送Request，等待RpcResponse");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            // 阻塞读RpcResponse
            RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject();
            System.out.println("返回rpcResponse");
            return rpcResponse;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
