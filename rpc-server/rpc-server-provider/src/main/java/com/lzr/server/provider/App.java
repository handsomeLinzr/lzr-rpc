package com.lzr.server.provider;

/**
 * 启动服务
 */
public class App {

    public static void main(String[] args) {
        // 启动服务
        RpcProxyServer.publish(8080);
    }

}
