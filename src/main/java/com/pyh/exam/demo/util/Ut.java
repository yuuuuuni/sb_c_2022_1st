package com.pyh.exam.demo.util;

public class Ut {

	public static boolean empty(Object obj) { // 범용적으로 쓰기 위해 Object 타입으로 함
		if(obj == null) {
			return true;
		}
		
		// 'obj의 객체타입이 String이다'라는 말이 false이면 true를 리턴해라
		if(obj instanceof String == false) { // instanceof는 객체 타입을 확인하는 연산자. 형변환이 가능한 지의 해당 여부를 true 또는 false로 반환해줌
			return true;
		}
		
		// if문을 통과해서 이 부분에 온거면 obj의 객체타입이 String이 맞다는 의미
		String str = (String)obj; // obj를 String으로 형변환하여 str에 담아줌
		
		return str.trim().length() == 0;
	}

}
