package com.raysmond.blog.microservice0.services;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Service
@RequestMapping("/RequestProcessorService")
public class RequestProcessorService {

    public String getRealIp(HttpServletRequest request) {
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp == null || request.getHeader("X-Real-IP").isEmpty()) {
            return request.getRemoteAddr();
        }
        return xRealIp;
    }

    public String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent;
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        HttpServletRequest request = new MockHttpServletRequest();

        switch (method) {
            case "all":
                for (int i = 0; i < 4; i++) {
                    getRealIp_test(request);
                }
                for (int i = 0; i < 1; i++) {
                    getUserAgent_test(request);
                }
                break;
            case "getRealIp":
                getRealIp_test(request);
                break;
            case "getUserAgent":
                getUserAgent_test(request);
                break;
        }

        return "test";
    }

    String getRealIp_test(HttpServletRequest request) {

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println("request: ");
//        System.out.println(RamUsageEstimator.sizeOf(request));
//        System.out.println(RamUsageEstimator.shallowSizeOf(request));
//        System.out.println(ClassLayout.parseInstance(request).toPrintable());

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp == null || request.getHeader("X-Real-IP").isEmpty()) {
            return request.getRemoteAddr();
        }
        return xRealIp;
    }

    String getUserAgent_test(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent;
    }

}
