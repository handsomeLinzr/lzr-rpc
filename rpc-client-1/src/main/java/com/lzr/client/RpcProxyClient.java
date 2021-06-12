package com.lzr.client;

import java.lang.reflect.Proxy;

public class RpcProxyClient {

    /**
     * 动态代理获得服务代理
     * @param <T>
     * @return
     */
    public <T> T clientProxy(Class<T> targetClass, String host, int port) {
        return (T) Proxy.newProxyInstance(targetClass.getClassLoader(), new Class[]{targetClass}, new RemoteInvocationHandler(host, port));
    }

}
