package com.pyh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.service.MemberService;
import com.pyh.exam.demo.util.Ut;
import com.pyh.exam.demo.vo.Member;
import com.pyh.exam.demo.vo.ResultData;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		if(Ut.empty(loginId)) { // loginId가 비어있는지 체크하는 함수
			return ResultData.from("F-1", "loginId(을)를 입력해주세요.");
		}
		
		if(Ut.empty(loginPw)) {
			return ResultData.from("F-2", "loginPw(을)를 입력해주세요.");
		}
		
		if(Ut.empty(name)) {
			return ResultData.from("F-3", "name(을)를 입력해주세요.");
		}
		
		if(Ut.empty(nickname)) {
			return ResultData.from("F-4", "nickname(을)를 입력해주세요.");
		}
		
		if(Ut.empty(cellphoneNo)) {
			return ResultData.from("F-5", "cellphoneNo(을)를 입력해주세요.");
		}
		
		if(Ut.empty(email)) {
			return ResultData.from("F-6", "email(을)를 입력해주세요.");
		}
		
		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
		if(joinRd.isFail()) { // resultCode가 F-로 시작하면
			return joinRd; // 값을 그대로 바로 리턴해라
		}
		
			int id = (int)joinRd.getData1();
		
			Member member = memberService.getMemberById(id);
			
			return ResultData.newData(joinRd, member); // newData 메소드 이용하여 joinRd의 resultCode, msg는 그대로 가져가되 Data1 부분만 member로 넣어줌)
			
	}
}