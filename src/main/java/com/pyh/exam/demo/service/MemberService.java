package com.pyh.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyh.exam.demo.repository.MemberRepository;
import com.pyh.exam.demo.util.Ut;
import com.pyh.exam.demo.vo.Member;
import com.pyh.exam.demo.vo.ResultData;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		// 로그인아이디 중복체크
		Member oldMember = getMemberByLoginId(loginId); // 브라우저에서 입력된 loginId를 db에서 찾아서 oldMember에 담아라
		
		if(oldMember != null) { // oldMember에 값이 있으면(즉, 브라우저에서 입력된 loginId가 db에서 찾아지면)
			return ResultData.from("F-7", Ut.f("해당 로그인아이디(%s)는 이미 사용중입니다.", loginId));
		}
		
		// 이름+이메일 중복체크
		oldMember = getMemberByNameAndEmail(name, email);
		
		if(oldMember != null) {
			return ResultData.from("F-8", Ut.f("해당 이름(%s)과 이메일(%s)은 이미 사용중입니다.", name, email));
		}
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email); // 회원 계정을 생성하는 메소드 먼저 호출하여 계정 생성 후,
		int id = memberRepository.getLastInsertId(); // 가장 마지막에 생성된 회원의 id를 구하는 메서드 호출 후 그 값 리턴
		
		return ResultData.from("S-1", "회원가입이 완료되었습니다.", id);
	}

	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}
