package com.pyh.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.pyh.exam.demo.vo.Rq;

@Component // @Autowired 하려면 @Component 붙여줘야함
public class BeforeActionInterceptor implements HandlerInterceptor {
	 @Override
	    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		 Rq rq = new Rq(req); // 인터셉터에서 Rq객체를 대표로 생성
		 req.setAttribute("rq", rq); // Rq객체인 rq의 이름을 "rq"라고 지정
		 // req는 일종의 카트. 여기서 대표로 만든 Rq객체를 Controller의 각 액션들이 가져가서 써야하므로,
		 // req라는 카트에 Rq객체를 setAttribute로 넣어주면 됨
		 
	        return HandlerInterceptor.super.preHandle(req, resp, handler);
	    }
}
