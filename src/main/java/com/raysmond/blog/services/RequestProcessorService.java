package com.raysmond.blog.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RequestProcessorService {

    public String getRealIp(HttpServletRequest request) {
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp == null || request.getHeader("X-Real-IP").isEmpty()) {
            return request.getRemoteAddr();
        }
        return xRealIp;
    }

}
