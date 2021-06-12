package com.lzr.rpc.server.provider;

import com.lzr.server.api.IHelloService;

public class HelloServiceImpl implements IHelloService {
    @Override
    public String hello(String name) {
        return String.format("hello %s!", name);
    }

    @Override
    public Integer add(Integer a, Integer b) {
        return a+b;
    }
}
