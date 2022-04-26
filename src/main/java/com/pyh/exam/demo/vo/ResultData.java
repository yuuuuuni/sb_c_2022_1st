package com.pyh.exam.demo.vo;

import lombok.Getter;
import lombok.ToString;

@ToString // 객체 위치의 내용을 알려줌
public class ResultData<DT> {
	@Getter // 자동으로 Getter 메소드를 생성해줌 (예를 들어, name라는 필드에 선언하면 자동으로 getName() 메소드를 생성해줌)
	private String resultCode; // 성공&실패의 값을 나타내는 코드
	@Getter
	private String msg; // 메세지
	@Getter
	private String data1Name; // 데이터네임
	@Getter
	private DT data1; // 데이터
	
	// 메소드 오버로딩 법칙 사용(data1이 안들어오는 경우)
	public static ResultData from(String resultCode, String msg) {
		return from(resultCode, msg, null, null); // data1이 없으므로 from 메소드 호출 시 들어가는 매개변수 자리에 맞춰 data1 자리에 null로 채워줘야함
	}
	
	public static <DT> ResultData<DT> from(String resultCode, String msg, String data1Name, DT data1) {
	// 메소드(리턴타입: ResultData, 메소드명: from)
		ResultData<DT> rd = new ResultData<DT>(); // 리턴타입을 ResultData로 하려면 ResultData 객체를 생성해야겠지?
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1Name = data1Name;
		rd.data1 = data1;

		return rd;
		
	}
	
	public boolean isSuccess() {
		return resultCode.startsWith("S-"); // resultCode가 "S-"로 시작되면 true 리턴. "S-"로 시작되지 않으면 false 리턴
	}
	
	public boolean isFail() {
		return isSuccess() == false; // isSuccess() 메소드가 false이면 true 리턴. 아니면 false 리턴
	}

	public static <DT> ResultData<DT> newData(ResultData oldRd, String data1Name, DT data1) {
		return from(oldRd.getResultCode(), oldRd.getMsg(), data1Name, data1);
	}

}
