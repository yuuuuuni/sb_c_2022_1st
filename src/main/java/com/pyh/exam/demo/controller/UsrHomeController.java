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
	
	@RequestMapping("/usr/home/getCount")
	@ResponseBody
	public int getCount() {
		return count;
	}
	
	@RequestMapping("/usr/home/doSetCount")
	@ResponseBody
	public String doSetCount(int count) { // url에서 doSetCount 뒤에 '?변수=값&변수=값&변수=값' 이런식으로 와야함. "?count=50" 이렇게!
		this.count = count;
		return "count의 값이 " + this.count + "으로 초기화 되었습니다.";
	}
}

/*
 * Request => 요청된것, 컴퓨터 입장에서는 받은편지
 * Response => 응답하는것, 컴퓨터 입장에서는 보낼편지
*/