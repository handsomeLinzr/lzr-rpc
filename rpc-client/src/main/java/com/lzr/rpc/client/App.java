package com.lzr.rpc.client;

import com.lzr.rpc.api.po.IHelloService;

public class App {

    public static void main(String[] args) {
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        IHelloService service = rpcProxyClient.clientProxy(IHelloService.class, "localhost", 8080);
        String hello = service.hello("阿哲");
        Integer add = service.add(2, 5);
        System.out.println(hello);
        System.out.println(add);
    }

}
