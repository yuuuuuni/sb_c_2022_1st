package com.pyh.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyh.exam.demo.repository.MemberRepository;
import com.pyh.exam.demo.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email); // 회원 계정을 생성하는 메소드 먼저 호출한 후,
		return memberRepository.getLastInsertId(); // 가장 마지막에 생성된 회원의 id를 구하는 메서드 호출 후 그 값 리턴
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}
