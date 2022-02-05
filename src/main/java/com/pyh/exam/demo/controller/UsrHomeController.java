package com.pyh.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 일종의 주석이라고 보면 됨. 스프링한테 아래의 클래스가 컨트롤러 라는 것을 알려줌(아래 메서드들을 품고 있으면 써줘야함)
public class UsrHomeController {
	@RequestMapping("/usr/home/main") // 이러한 url로 접속이 요청되면
	@ResponseBody // 아래 함수가 리턴한 값(즉, Body를)을 응답한다(화면에 띄워진다)
	public String showMain() {	// showMain()이라는 이 함수가 실행됨. 이 때 showMain() 메서드는 그냥 이름을 지은 것 ddd 이런식으로 지어도 됨
		return "안녕하세요.";
	}
	
	@RequestMapping("/usr/home/main2")
	@ResponseBody
	public String showMain2() {
		return "반갑습니다.";
	}
	
	@RequestMapping("/usr/home/main3")
	@ResponseBody
	public String showMain3() {
		return "또만나요.";
	}
	
}

/*
 * Request => 요청된것, 컴퓨터 입장에서는 받은편지
 * Response => 응답하는것, 컴퓨터 입장에서는 보낼편지
*/