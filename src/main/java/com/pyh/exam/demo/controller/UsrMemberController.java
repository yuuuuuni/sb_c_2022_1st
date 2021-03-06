package com.pyh.exam.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.service.MemberService;
import com.pyh.exam.demo.util.Ut;
import com.pyh.exam.demo.vo.Member;
import com.pyh.exam.demo.vo.ResultData;
import com.pyh.exam.demo.vo.Rq;

@Controller
public class UsrMemberController {
	private MemberService memberService;
	private Rq rq;
	
	public UsrMemberController(MemberService memberService, Rq rq) {
		this.memberService = memberService;
		this.rq = rq;
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) { // 제너릭을 이용하여 리턴타입을 ResultData 형식이긴 하면서(3개 받는거), 3번째 데이터(Data1)의 타입이 Member인 것만 받게 하도록 변경
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
		
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email); // int로 형변환 할 필요 없이 제너릭을 이용하여 애초에 ResultData의 형식을 <Integer>로 받음
		
		if(joinRd.isFail()) { // resultCode가 F-로 시작하면
			return (ResultData)joinRd; // 값을 그대로 바로 리턴해라
		}
		
			int id = joinRd.getData1(); // int로 형변환 하는 것을 없앰
		
			Member member = memberService.getMemberById(id);
			
			return ResultData.newData(joinRd, "member", member); // newData 메소드 이용하여 joinRd의 resultCode, msg는 그대로 가져가되 Data1 부분만 member로 넣어줌)
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw) { // rq를 적용시킬 것이므로 rq를 갖고 있는 req 먼저 받아와야함
		if(rq.isLogined()) {
			return rq.jsHistoryBack("이미 로그인 된 상태입니다.");
		}
		
		if(Ut.empty(loginId)) {
			return rq.jsHistoryBack("loginId(을)를 입력해주세요.");
		}
		
		if(Ut.empty(loginPw)) {
			return rq.jsHistoryBack("loginPw(을)를 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId); // member에는 db에 있는 정보가 담기는 것임
		
		// 브라우저에서 입력된 loginId에 대한 회원 정보가 없는 경우
		if(member == null) {
			return rq.jsHistoryBack("존재하지 않는 로그인아이디 입니다.");
		}
		
		// 입력된 loginId에 대한 회원 정보는 있지만 그 값(member)의 비밀번호가 브라우저에서 입력된 비밀번호(loginPw)와 같지 않은 경우
		if(member.getLoginPw().equals(loginPw) == false) {
			return rq.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		// 여기서부터는 로그인 성공
		
		rq.login(member); // session에서 직접 지정하지 말고 rq 적용시켜 rq로 불러오기
		
		return rq.jsReplace(Ut.f("%s님 환영합니다.", member.getNickname()), "/"); // 정상적으로 로그인이 되면 메인 페이지로 가도록 uri를 "/"로 지정
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout() {
		if(!rq.isLogined()) {
			return rq.jsHistoryBack("이미 로그아웃 상태입니다.");
		}
		
		// 여기서부터는 로그아웃 성공
		
		rq.logout();
		
		return rq.jsReplace("로그아웃 되었습니다.", "/");
	}
}