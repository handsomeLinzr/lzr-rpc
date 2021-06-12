package com.lzr.server.provider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

public class RpcProxyServer {

    private static ThreadPoolExecutor threadPool;
    private static ConcurrentHashMap<String, Object> map;
    static {
        threadPool = new ThreadPoolExecutor(
                10,
                10,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadFactory() {
                    int i;
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "RPC-LZR-pool-" + i++);
                    }
                });

        map = new ConcurrentHashMap<>(16);
        map.put("IHelloService", new HelloServiceImpl());
    }

    /**
     * 发布服务到端口
     * @param port
     */
    public static void publish(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("开始监听端口：" + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("请求进入");
                // 将请求抛给线程池
                threadPool.execute(new ProcessorHandler(socket, map));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
