package com.pyh.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 일종의 주석이라고 보면 됨. 스프링한테 아래의 클래스가 컨트롤러 라는 것을 알려줌(아래 메서드들을 품고 있으면 써줘야함)
public class UsrHomeController {
	private int count; // count가 값을 기억하도록 전역변수로 옮김. 여기서는 변수만 선언
	
	public UsrHomeController() { // 생성자 생성
		count = 0; // 여기서 초기값을 0으로 해줌
	}
	
	@RequestMapping("/usr/home/main") // 이렇게 접속이 요청되면
	@ResponseBody // 아래 함수가 리턴한 값(즉, Body를)을 응답한다
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
	
	@RequestMapping("/usr/home/main4")
	@ResponseBody
	public int showMain4() {
		return count++; // 후위연산자. count 값을 먼저 리턴하고 뒤늦게 1증가
	}
	
	@RequestMapping("/usr/home/main5")
	@ResponseBody
	public String showMain5() {
		count = 0;
		return "count의 값이 0으로 초기화 되었습니다.";
	}
}

/*
 * Request => 요청된것, 컴퓨터 입장에서는 받은편지
 * Response => 응답하는것, 컴퓨터 입장에서는 보낼편지
*/