package com.pyh.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component // @Autowired 하려면 @Component 붙여줘야함
public class BeforeActionInterceptor implements HandlerInterceptor {
	 @Override
	    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		 System.out.println("실행되니?");
		 
	        return HandlerInterceptor.super.preHandle(req, resp, handler);
	    }
}
