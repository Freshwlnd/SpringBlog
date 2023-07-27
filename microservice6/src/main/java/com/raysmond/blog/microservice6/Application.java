package com.raysmond.blog.microservice6;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * @author Raysmond<i@raysmond.com>
 */
@SpringBootApplication
// 开启缓存请把下行取消注释
// Open the cache Please uncomment the downlink
//@EnableCaching
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.raysmond.blog.microservice6.client")
public class Application {

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        SpringApplication.run(Application.class, args);
    }

}
