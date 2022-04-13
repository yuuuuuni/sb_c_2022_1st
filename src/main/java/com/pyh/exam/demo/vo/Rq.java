package com.pyh.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.pyh.exam.demo.service.MemberService;
import com.pyh.exam.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // value = "request"이라는 뜻은 객체가 싱글톤(객체가 하나만 존재)이 아니라 각 요청별 하나씩 존재해야한다는 뜻 
public class Rq {
	@Getter // @Getter를 달음으로써, isLogined() 메소드 생성됨 (Outline에서 확인 가능) 
	private boolean isLogined;
	@Getter // @Getter를 달음으로써, getLoginedMemberId() 메소드 생성됨
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) { // 생성자
		this.req = req;
		this.resp = resp;
		
		this.session = req.getSession(); // req를 통해서 Session을 얻을 수 있음
		
		boolean isLogined = false; // 로그인을 안했다고 가정
		int loginedMemberId = 0; // 로그인을 안했다고 가정
		Member loginedMember = null;
		
		// 로그인 했는지 체크
		if(session.getAttribute("loginedMemberId") != null) { // loginedMemberId 안에 로그인한 회원의 id가 들어있다는 뜻
			isLogined = true; // 로그인한 상태로 하겠다
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}
		
		this.isLogined = isLogined; // isLogined의 값을 Rq한테 전달 (왜? Rq가 대신 일을 하려면 isLogined와 loginedMemberId의 최종값을 가지고 있어야하기 때문) 
		this.loginedMemberId = loginedMemberId; // loginedMemberId의 값을 Rq한테 전달
		this.loginedMember = loginedMember;
		
		this.req.setAttribute("rq", this);
	}

	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8"); // 한글 안깨지도록 해줌
		
		print(Ut.jsHistoryBack(msg));
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void println(String str) {
		print(str + "\n");
		
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId()); // session에 로그인된 회원번호를 꺼내서 'loginedMemberId'라는 이름으로 값을 넣어주겠다.
	}

	public void logout() {
		session.removeAttribute("loginedMemberId"); // '로그인된 회원번호'라는 값을 삭제하겠다.
	}

	public String historyBackJsOnView(String msg) {
		req.setAttribute("historyBack", true);
		req.setAttribute("msg", msg);
		return "common/js";
	}

	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}
	
	// 이 메서드는 Rq 객체가 자연스럽게 생성되도록 유도하는 역할을 한다.
	// 지우면 안되고,
	// 편의를 위해 BeforeActionInterceptor 에서 꼭 호출해줘야 한다.
	public void initOnBeforeActionInterceptor() {
		
		
	}
}
