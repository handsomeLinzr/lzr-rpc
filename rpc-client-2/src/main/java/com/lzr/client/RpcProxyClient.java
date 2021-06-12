package com.lzr.client;

import java.lang.reflect.Proxy;

/**
 * @author Administrator
 */
public class RpcProxyClient {

    public <T> T getProxyClient(Class<T> clazz, String host, int port, String version) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RemoteInvocationHandler(host, port, version));
    }

}
