package com.pyh.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.pyh.exam.demo.vo.Rq;

@Component // @Autowired 하려면 @Component(@Service, @Mapper로도 대체가능) 붙여줘야함
public class BeforeActionInterceptor implements HandlerInterceptor {
	private Rq rq;
	
	public BeforeActionInterceptor(Rq rq) {
		this.rq = rq;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		rq.initOnBeforeActionInterceptor(); // rq를 강제로 활성화시키기 위해 rq에 빈 메소드를 만들어 호출

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}


/*
req.setAttribute("rq", rq);
- 브라우저의 ip, 쿠키, url 같은 이런 모든 요청들이 req에 들어옴(받은 편지)
- 이 req에 "rq"라는 이름으로 rq 값(로그인여부, 로그인된회원번호)을 넣어주겠다.
- 이걸 이제 인터셉터에 지정해놓음으로써, resource나 error 경로를 제외한 모든 경로의 req에 rq값이 넣어짐
- 그러면 다른 곳에서 getAttribute로 불러와서 사용하면 된다.
  - 컨트롤러, 서비스, 리포지터리, JSP단에서도 get으로 접근 가능
  - 근데 보통 서비스, 리포지터리에서는 잘 안부르고 컨트롤러랑 JSP에서 씀
  
속성을 지정한다(setAttribute) = 내가 지정한 변수이름에 값을 넣겠다.

* Request에는 세션도 딸려있음(그래서 req.getSession()하면 꺼내와진다.)
*/