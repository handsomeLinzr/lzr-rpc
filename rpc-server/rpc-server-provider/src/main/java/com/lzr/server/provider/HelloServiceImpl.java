package com.lzr.server.provider;

import com.lzr.rpc.api.po.IHelloService;

/**
 * ζε‘ζδΎ
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
