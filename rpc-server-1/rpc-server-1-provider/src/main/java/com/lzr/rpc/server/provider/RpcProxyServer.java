package com.lzr.rpc.server.provider;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 启动socket等待连接
 */
@Slf4j
public class RpcProxyServer {

    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            10,
            10,
            30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(30),
            new ThreadFactory() {
                int i = 0;
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "RPC-LZR-POOL-" + i++);
                }
            });

    /**
     * 发布服务
     */
    public void publish(Object service, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            log.info("服务启动：{}", port);
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new ProcessorHandler(socket, service));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
