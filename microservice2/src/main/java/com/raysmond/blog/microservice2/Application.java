package com.raysmond.blog.microservice2;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Raysmond<i @ raysmond.com>
 */
@SpringBootApplication
// 开启缓存请把下行取消注释
// Open the cache Please uncomment the downlink
//@EnableCaching
public class Application {

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        SpringApplication.run(Application.class, args);
    }

}
