package com.lzr.rpc.server.provider;

public class App {

    public static void main(String[] args) {
        // 发布服务
        RpcProxyServer proxyServer = new RpcProxyServer();
        proxyServer.publish(new HelloServiceImpl(), 8081);
    }
}
