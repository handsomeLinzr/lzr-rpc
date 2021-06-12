package com.lzr.rpc.server.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private int port;
    private Map<String, Object> serviceMap;

    public RpcServer(int port) {
        this.port = port;
    }

    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new ThreadFactory() {
        int i = 0;
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "LZR-POOL-" + i++);
        }
    });

    @Override
    public void afterPropertiesSet() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            log.info("服务端启动：{}", port);
            while (true) {
                Socket socket = serverSocket.accept();
                // 交给线程池处理
                threadPool.execute(new ProcessorHandler(socket, serviceMap));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获得注解的所有bean
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RpcService.class);
        serviceMap = new ConcurrentHashMap<>(beans.size());
        for (Object bean : beans.values()) {
            // 筛选保存
            RpcService annotation = bean.getClass().getAnnotation(RpcService.class);
            String beanName = annotation.value().getName();
            if (!StringUtils.isEmpty(annotation.version())) {
                beanName += "-" + annotation.version();
            }
            serviceMap.put(beanName, bean);
        }
    }
}
