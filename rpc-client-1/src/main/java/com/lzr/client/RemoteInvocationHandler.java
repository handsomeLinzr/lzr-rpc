package com.lzr.client;

import com.lzr.server.api.RpcRequest;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务的动态代理
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private final String host;
    private final int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest(method.getDeclaringClass().getName(), method.getName(), args);
        RpcNetTransport rpcNetTransport = new RpcNetTransport(host, port);
        return rpcNetTransport.send(request);
    }
}
