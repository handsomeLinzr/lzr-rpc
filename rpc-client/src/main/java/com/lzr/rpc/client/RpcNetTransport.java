package com.lzr.rpc.client;

import com.alibaba.fastjson.JSON;
import com.lzr.rpc.api.po.RpcRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * rpc远程调用
 */
public class RpcNetTransport {

    private String host;
    private int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(RpcRequest rpcRequest) {
        try (Socket socket = new Socket(host, port);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            String info = JSON.toJSONString(rpcRequest);
            printWriter.println(info);
            printWriter.println();
            printWriter.flush();

            StringBuilder stringBuilder = new StringBuilder();
            String content;
            while ((content = bufferedReader.readLine()) != null && !"".equals(content)) {
                stringBuilder.append(content);
            }
            Object result = JSON.parseObject(stringBuilder.toString(), rpcRequest.getTargetClass());
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
