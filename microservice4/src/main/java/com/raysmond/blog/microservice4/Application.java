package com.raysmond.blog.microservice4;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


/**
 * @author Raysmond<i@raysmond.com>
 */
@SpringBootApplication
// 开启缓存请把下行取消注释
// Open the cache Please uncomment the downlink
//@EnableCaching
// 启用 Spring Data 的 Web 支持，包括对Pageable对象的正确序列化和反序列化。
@EnableSpringDataWebSupport
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.raysmond.blog.microservice4.client")
public class Application {

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        SpringApplication.run(Application.class, args);
    }

}
