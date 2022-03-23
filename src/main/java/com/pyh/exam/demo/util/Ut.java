package com.pyh.exam.demo.util;

public class Ut {

	public static boolean empty(Object obj) { // 범용적으로 쓰기 위해 Object 타입으로 함
		if(obj == null) { // 비어있는지 체크
			return true;
		}
		
		// 'obj의 객체타입이 String이다'라는 말이 false이면 true를 리턴해라
		if(obj instanceof String == false) { // 문자열인지 체크. instanceof는 객체 타입을 확인하는 연산자. 형변환이 가능한 지의 해당 여부를 true 또는 false로 반환해줌
			return true;
		}
		
		// 2개의 if문을 통과해서 여기에 온거면 obj는 null이 아니며, obj의 객체타입이 String이 맞다는 의미
		String str = (String)obj; // 현재 타입이 Object인 obj를 String으로 형변환하여 str에 담아줌
		
		return str.trim().length() == 0; // str의 공백을 제거한 길이가 0과 같다면 true 리턴, 아니라면 false 리턴
	}

	public static String f(String format, Object... args) {
		return String.format(format, args);
	}

	public static String jsHistoryBack(String msg) {
		if(msg == null) {
			msg = "";
		}
		
		return Ut.f("""
				<script>
				const msg = '%s'.trim();
				if(msg.length > 0) {
					alert(msg);
				}
				history.back();
				</script>
				""", msg);
	}

	public static String jsReplace(String msg, String uri) {
		if(msg == null) {
			msg = "";
		}
		
		if(uri == null) {
			uri = "";
		}
		
		return Ut.f("""
				<script>
				const msg = '%s'.trim();
				if(msg.length > 0) {
					alert(msg);
				}
				location.replace('%s');
				</script>
				""", msg, uri);
	}

}
