package com.wzr.yi.rpc.core.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @autor zhenrenwu
 * @date 2022/6/12 2:01 上午
 */
public class RpcServer {
    private final ThreadPoolExecutor threadPoolExecutor;

    private final HashMap<String, Object> registerteredService;
    public RpcServer(){
        System.out.println("初始化线程池，以及服务注册");
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 500;
        int queueSize = 100;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(queueSize);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if(!executor.isShutdown()) {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                 workingQueue, threadFactory, handler);
        this.registerteredService = new HashMap<>();
    }

    //参数service就是interface的implementation的 object
    public void register(Object service){
        System.out.println(String.format("将%s注册到服务注册器中", service.getClass().getInterfaces()[0].getName()));
        registerteredService.put(service.getClass().getInterfaces()[0].getName(), service);
    }

    public void server(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(String.format("server starting ...注册端口为%s", port));
            Socket handleSocket;
            while ((handleSocket = serverSocket.accept()) != null){
                System.out.println("client connected, ip:" + handleSocket.getInetAddress());
                threadPoolExecutor.execute(new RpcServerWorker(handleSocket, registerteredService));
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(String.format("连接超时，%s", e.getMessage()));
        }
    }
}
