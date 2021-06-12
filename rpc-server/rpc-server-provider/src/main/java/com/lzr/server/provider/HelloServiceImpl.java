package com.lzr.server.provider;

import com.lzr.rpc.api.po.IHelloService;

/**
 * 服务提供
 */
public class HelloServiceImpl implements IHelloService {
    @Override
    public String hello(String name) {
        return "hello " + name;
    }

    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }
}
