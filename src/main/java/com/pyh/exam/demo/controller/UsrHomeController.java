package com.pyh.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 일종의 주석이라고 보면 됨. 스프링한테 아래의 클래스가 컨트롤러 라는 것을 알려줌(아래 메서드들을 품고 있으면 써줘야함)
public class UsrHomeController {
	@RequestMapping("/usr/home/main")
	public String showMain() {
		return "usr/home/main";
	}
	
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/usr/home/main";
	}
}




/*
 * Request => 요청된것, 컴퓨터 입장에서는 받은편지
 * Response => 응답하는것, 컴퓨터 입장에서는 보낼편지
*/