//package com.wzr.yi.rpc.Test;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//
///**
// * @autor zhenrenwu
// * @date 2022/6/12 3:06 下午
// */
//public class TestConnect {
//    public static void main(String[] args) {
//        // 接收客户端发来的数据 最大2048
//        byte bytes[] = new byte[2048];
//        ServerSocket serverSocket = null;
//        //监听 一个端口
//        try{
//            serverSocket = new ServerSocket(9001);
//            System.out.println("server is starting...");
//            boolean stopFlag = false;
//            Socket clientSocket = serverSocket.accept();
//            System.out.println("success" + clientSocket.getInetAddress().getHostAddress());
////            while (!stopFlag){
////                // BIO 这里会阻塞
////                int read = clientSocket.getInputStream().read(bytes);
////
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
