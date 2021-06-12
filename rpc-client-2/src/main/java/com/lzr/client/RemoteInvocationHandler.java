package com.lzr.client;

import com.lzr.server.api.RpcRequest;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {

    private final String host;
    private final int port;
    private final String version;

    public RemoteInvocationHandler(String host, int port, String version) {
        this.host = host;
        this.port = port;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 封装request信息
        RpcRequest request = new RpcRequest(method.getDeclaringClass().getName(), method.getName(), args);
        if (!StringUtils.isEmpty(version)) {
            request.setVersion(version);
        }
        // 封装远程调用
        RpcNetTransport rpcNetTransport = new RpcNetTransport(host, port);
        // 调用服务并返回
        return rpcNetTransport.send(request);
    }
}
