package com.pyh.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller /* 아래 메서드들을 품고 있으면 써줘야함 */
public class UsrHomeController {
	@RequestMapping("/usr/home/main") /* 이렇게 접속이 요청되면 */
	@ResponseBody
	public String showMain() {	/* showMain()이라는 이 함수가 실행되어 화면에 띄워진다 */
		return "안녕";
	}
}

/*
 * Request => 요청된것, 컴퓨터 입장에서는 받은편지
 * Response => 응답하는것, 컴퓨터 입장에서는 보낼편지
*/