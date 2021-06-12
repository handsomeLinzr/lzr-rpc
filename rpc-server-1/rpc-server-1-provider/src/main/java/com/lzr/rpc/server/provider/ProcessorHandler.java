package com.lzr.rpc.server.provider;

import com.lzr.server.api.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

@Slf4j
public class ProcessorHandler implements Runnable{

    private Socket socket;
    private Object service;

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
            RpcRequest request = (RpcRequest) objectInputStream.readObject();
            log.info("接收并处理请求：{}", request);
            // 调用本地
            Object result = invoke(request);
            // socket流将结果返回
            objectOutputStream.writeObject(result);
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // 调用本地服务
    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] params = request.getParams();
        // 形参
        Class<?>[] paramTypes = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = params[i].getClass();
        }
        Class<?> serviceClass = Class.forName(request.getClassName());
        Method method = serviceClass.getMethod(request.getMethodName(), paramTypes);
        return method.invoke(service, params);
    }
}
