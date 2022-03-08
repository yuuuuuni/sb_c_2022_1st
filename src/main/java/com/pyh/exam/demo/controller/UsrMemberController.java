package com.pyh.exam.demo.controller;

import javax.servlet.http.HttpSession;

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
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public ResultData doLogin(HttpSession httpSession, String loginId, String loginPw) { // HttpSession httpSession을 적어주면 알아서 sessione을 얻어와준다.
		boolean isLogined = false;
		
		if(httpSession.getAttribute("loginedMemberId") != null) { // 이미 로그인 되어있는 상태이면
			isLogined = true;
		}
		
		if(isLogined) {
			return ResultData.from("F-5", "이미 로그인 된 상태입니다.");
		}
		
		if(Ut.empty(loginId)) {
			return ResultData.from("F-1", "loginId(을)를 입력해주세요.");
		}
		
		if(Ut.empty(loginPw)) {
			return ResultData.from("F-2", "loginPw(을)를 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId); // member에는 db에 있는 정보가 담기는 것임
		
		// 브라우저에서 입력된 loginId에 대한 회원 정보가 없는 경우
		if(member == null) {
			return ResultData.from("F-3", "존재하지 않는 로그인아이디 입니다.");
		}
		
		// 입력된 loginId에 대한 회원 정보는 있지만 그 값(member)의 비밀번호가 브라우저에서 입력된 비밀번호(loginPw)와 같지 않은 경우
		if(member.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-4", "비밀번호가 일치하지 않습니다.");
		}
		
		// 여기서부터는 로그인 성공
		
		httpSession.setAttribute("loginedMemberId", member.getId()); // 로그인된 회원의 id를 꺼내서 '로그인된 회원의 id'라고 이름을 지정하겠다.
		
		return ResultData.from("S-1", Ut.f("%s님 환영합니다.", member.getNickname()));
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public ResultData doLogout(HttpSession httpSession) {
		boolean isLogined = false;
		
		if(httpSession.getAttribute("loginedMemberId") == null) { // 이미 로그아웃 되어있는 상태이면
			isLogined = true;
		}
		
		if(isLogined) {
			return ResultData.from("S-1", "이미 로그아웃 상태입니다.");
		}
		
		
		httpSession.removeAttribute("loginedMemberId"); // '로그인된 회원의 id'라는 속성을 삭제하겠다.
		
		return ResultData.from("S-2", "로그아웃 되었습니다.");
	}
}