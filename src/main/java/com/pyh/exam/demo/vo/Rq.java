package com.pyh.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pyh.exam.demo.util.Ut;

import lombok.Getter;

public class Rq {
	@Getter // @Getter를 달음으로써, isLogined() 메소드 생성됨 (Outline에서 확인 가능) 
	private boolean isLogined;
	@Getter // @Getter를 달음으로써, getLoginedMemberId() 메소드 생성됨
	private int loginedMemberId;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	public Rq(HttpServletRequest req, HttpServletResponse resp) { // 생성자
		this.req = req;
		this.resp = resp;
		
		this.session = req.getSession(); // req를 통해서 Session을 얻을 수 있음
		
		boolean isLogined = false; // 로그인을 안했다고 가정
		int loginedMemberId = 0; // 로그인을 안했다고 가정
		
		// 로그인 했는지 체크
		if(session.getAttribute("loginedMemberId") != null) { // loginedMemberId 안에 로그인한 회원의 id가 들어있다는 뜻
			isLogined = true; // 로그인한 상태로 하겠다
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
		}
		
		this.isLogined = isLogined; // isLogined의 값을 Rq한테 전달 (왜? Rq가 대신 일을 하려면 isLogined와 loginedMemberId의 최종값을 가지고 있어야하기 때문) 
		this.loginedMemberId = loginedMemberId; // loginedMemberId의 값을 Rq한테 전달
	}

	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8"); // 한글 안깨지도록 해줌
		
		println("<script>");
		
		if(!Ut.empty(msg)) {
			println("alert('" + msg + "');");
		}
		
		println("history.back();");
		
		println("</script>");
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
		session.setAttribute("loginedMemberId", member.getId());
		
	}
}
