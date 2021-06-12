package com.lzr.client;

import com.lzr.server.api.IHelloService;

public class App {

    public static void main(String[] args) {
        RpcProxyClient client = new RpcProxyClient();
        IHelloService helloService = client.clientProxy(IHelloService.class, "localhost", 8081);
        System.out.println(helloService.hello("lzr"));
        System.out.println(helloService.add(23, 45));
    }

}
