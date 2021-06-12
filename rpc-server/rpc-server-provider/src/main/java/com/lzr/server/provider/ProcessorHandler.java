package com.lzr.server.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzr.rpc.api.po.RpcRequest;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessorHandler implements Runnable{

    private Socket socket;
    private ConcurrentHashMap<String, Object> map;

    public ProcessorHandler(Socket socket, ConcurrentHashMap<String, Object> map) {
        this.socket = socket;
        this.map = map;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)){
            StringBuilder json = new StringBuilder();
            String content;
            while ((content = bufferedReader.readLine()) != null && !"".equals(content)) {
                json.append(content);
            }
            RpcRequest rpcRequest = JSON.parseObject(json.toString(), RpcRequest.class);

            Object result = invoke(rpcRequest);
            String to = JSON.toJSONString(result);
            printWriter.println(JSON.toJSONString(to));
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object invoke(RpcRequest rpcRequest) {
        try {
            // service对象
            Object service = map.get(rpcRequest.getClassName());
            Object[] params = rpcRequest.getParams();
            Class<?>[] paramTypes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                paramTypes[i] = params[i].getClass();
            }
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), paramTypes);
            return method.invoke(service, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
