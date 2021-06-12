package com.lzr.client;

import com.lzr.server.api.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 处理远程调用
 */
@Slf4j
public class RpcNetTransport {

    private final String host;
    private final int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object send(RpcRequest rpcRequest) {
        try (Socket socket = new Socket(host, port); 
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); 
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())){
            log.info("处理远程调用：{}:{}", host, port );

            objectOutputStream.writeObject(rpcRequest);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
