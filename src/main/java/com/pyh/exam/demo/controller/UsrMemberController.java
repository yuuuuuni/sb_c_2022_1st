package com.pyh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.service.MemberService;
import com.pyh.exam.demo.vo.Member;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		if(loginId == null || loginId.trim().length() == 0) { // loginId가 null이거나 공백을 제거한 길이가 0이면(즉, 공백이면) / trim 메소드는 공백을 다 제거해줌
			return "loginId(을)를 입력해주세요.";
		}
		
		if(loginPw == null || loginPw.trim().length() == 0) { // loginPw가 아예 없거나 들어왔는데 그 값이 공백이면
			return "loginPw(을)를 입력해주세요.";
		}
		
		if(name == null || name.trim().length() == 0) {
			return "name(을)를 입력해주세요.";
		}
		
		if(nickname == null || nickname.trim().length() == 0) {
			return "nickname(을)를 입력해주세요.";
		}
		
		if(cellphoneNo == null || cellphoneNo.trim().length() == 0) {
			return "cellphoneNo(을)를 입력해주세요.";
		}
		
		if(email == null || email.trim().length() == 0) {
			return "email(을)를 입력해주세요.";
		}
		
		int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
		if(id == -1) {
			return "해당 loginId(은)는 이미 사용중입니다.";
		}
		
		Member member = memberService.getMemberById(id);
		
		return member;
	}
}