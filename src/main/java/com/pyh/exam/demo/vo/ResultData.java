package com.pyh.exam.demo.vo;

import lombok.Getter;

public class ResultData {
	@Getter // 자동으로 Getter 메소드를 생성해줌 (예를 들어, name라는 필드에 선언하면 자동으로 getName() 메소드를 생성해줌)
	private String resultCode; // 성공&실패의 값을 나타내는 코드
	@Getter
	private String msg; // 메세지
	@Getter
	private Object data1; // 데이터
	
	private ResultData() {
		
	}
	
	// 메소드(리턴타입: ResultData, 메소드명: from)
	public static ResultData from(String resultCode, String msg, Object data1) {
		ResultData rd = new ResultData();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1 = data1;
			
		return rd;
	}
	
	public boolean isSuccess() {
		return resultCode.startsWith("S-"); // resultCode가 S-로 시작되면
	}
	
	public boolean isFail() {
		return isSuccess() == false;
	}
}
