package com.lzr.rpc.client;

import java.lang.reflect.Proxy;

public class RpcProxyClient {

    /**
     * 创建远程代理
     * @param <T>
     * @return
     */
    public <T> T clientProxy(Class<T> interfaceCls, String host, int port) {
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class[]{interfaceCls}, new RemoteInvocationHandler(host, port));
    }

}
