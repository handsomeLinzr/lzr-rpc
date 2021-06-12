package com.lzr.client;

import com.lzr.server.api.IPaymentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient client = applicationContext.getBean(RpcProxyClient.class);
        IPaymentService paymentService = client.getProxyClient(IPaymentService.class, "localhost", 8082, "2");
        System.out.println(paymentService.get());
    }

}
