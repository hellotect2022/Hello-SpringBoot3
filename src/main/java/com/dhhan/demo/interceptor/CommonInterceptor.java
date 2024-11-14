package com.dhhan.demo.interceptor;

import com.dhhan.demo.utils.LogHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LogHelper.debug("testtesttest",this);
        LogHelper.info("infotesttest",this);
        //if (false) throw new Exception("handler 에서 에러반환함");
        return true;
    }
}
