package com.pyh.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 자동으로 GetterSetter를 만들어줌(즉, get(), set() 메소드를 일일이 만들지 않아도 .get~, .set~를 쓸 수 있다는 뜻)
@NoArgsConstructor // 인자 없는 생성자까지 가능하도록
@AllArgsConstructor // 이걸 쓰면 생성자 따로 만들어줄 필요 없이 new Article(1, "제목1") 이렇게 써주면 생성자가 자동으로 만들어짐
public class Article {
	private int id; // private로 하면 자신만 접근 가능하므로 클래스 위에 @Data를 붙여서 접근 가능하도록 함
	private String regDate;
	private String updateDate;
	private int memberId; // 회원번호 필드 추가
	private String title;
	private String body;
	
	private String extra__writerName;
	private boolean extra__actorCanDelete;
}
