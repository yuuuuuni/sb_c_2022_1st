package com.pyh.exam.demo.vo;

import lombok.Getter;

public class ResultData {
	@Getter // 자동으로 Getter 메소드를 생성해줌 (예를 들어, name라는 필드에 선언하면 자동으로 getName() 메소드를 생성해줌)
	private String resultCode; // 성공&실패의 값을 나타내는 코드
	@Getter
	private String msg; // 메세지
	@Getter
	private Object data1; // 데이터
	
	private ResultData() { // private를 생성자로 하면 외부에서 객체 생성을 못함
		// 외부에서 객체 생성을 못하게 하고 여기에서 객체 생성(ResultData rd = new ResultData();)을 하려고 이렇게 ResultData 클래스를 private 한 것 같음
	}
	
	// 메소드 오버로딩 법칙 사용(data1이 안들어오는 경우)
	public static ResultData from(String resultCode, String msg) {
		return from(resultCode, msg, null); // data1이 없으므로 from 메소드 호출 시 들어가는 매개변수 자리에 맞춰 data1 자리에 null로 채워줘야함
	}
	
	public static ResultData from(String resultCode, String msg, Object data1) {
	// 메소드(리턴타입: ResultData, 메소드명: from)
		ResultData rd = new ResultData(); // 리턴타입을 ResultData로 하려면 ResultData 객체를 생성해야겠지?
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1 = data1;

		return rd;
		
	}
	
	public boolean isSuccess() {
		return resultCode.startsWith("S-"); // resultCode가 "S-"로 시작되면 true 리턴. "S-"로 시작되지 않으면 false 리턴
	}
	
	public boolean isFail() {
		return isSuccess() == false; // isSuccess() 메소드가 false이면 true 리턴. 아니면 false 리턴
	}
}
