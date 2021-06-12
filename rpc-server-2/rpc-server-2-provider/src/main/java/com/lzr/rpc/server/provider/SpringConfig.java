package com.lzr.rpc.server.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.lzr.rpc.server.provider")
public class SpringConfig {

    @Bean
    public RpcServer rpcServer() {
        return new RpcServer(8082);
    }

}
