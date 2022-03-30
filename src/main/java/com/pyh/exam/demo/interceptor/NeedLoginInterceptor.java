package com.pyh.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.pyh.exam.demo.util.Ut;
import com.pyh.exam.demo.vo.Rq;

@Component // @Autowired 하려면 @Component(@Service, @Mapper로도 대체가능) 붙여줘야함
public class NeedLoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		Rq rq = (Rq)req.getAttribute("rq"); // beforeAction인터셉터에서 req에 rq를 넣어놨으니깐 꺼내면 됨
		
		if(!rq.isLogined()) {
			rq.printHistoryBackJs("로그인 후 이용해주세요.");
			return false; // 꼭 해줘야함 규칙. 얘를 해주면 딱 얘 선에서 막히고 끝나게 함
		}
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}
