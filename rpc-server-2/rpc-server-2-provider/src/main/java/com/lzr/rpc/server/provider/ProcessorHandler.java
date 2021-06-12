package com.lzr.rpc.server.provider;

import com.lzr.server.api.RpcRequest;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author Administrator
 */
public class ProcessorHandler implements Runnable{

    private final Socket socket;
    private final Map<String, Object> serviceMap;

    public ProcessorHandler(Socket socket, Map<String, Object> serviceMap) {
        this.socket = socket;
        this.serviceMap = serviceMap;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
            RpcRequest request = (RpcRequest) objectInputStream.readObject();
            // 处理请求并写出去socket
            objectOutputStream.writeObject(invoke(request));
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 类信息
        Class<?> serviceClass = Class.forName(request.getClassName());
        // 方法信息
        Method method;
        // 实参
        Object[] params = request.getParams();
        if (null != params) {
            // 形参
            Class<?>[] paramTypes = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                paramTypes[i] = params[i].getClass();
            }
            method = serviceClass.getMethod(request.getMethodName(), paramTypes);
        } else {
            method = serviceClass.getMethod(request.getMethodName());
        }

        String className = request.getClassName();
        if (!StringUtils.isEmpty(request.getVersion())) {
            className += "-"+request.getVersion();
        }
        // 从map中获得实例对象
        Object service = serviceMap.get(className);
        return method.invoke(service, params);
    }
}
