package com.pyh.exam.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.vo.Article;

@Controller // 일종의 주석이라고 보면 됨. 스프링한테 아래의 클래스가 컨트롤러 라는 것을 알려줌(아래 메서드들을 품고 있으면 써줘야함)
public class UsrHomeController {
	@RequestMapping("/usr/home/getString")
	@ResponseBody
	public String getString() { // 리턴타입이 String
		return "HI";
	}
	
	@RequestMapping("/usr/home/getInt")
	@ResponseBody
	public int getInt() { // 리턴타입이 int
		return 10;
	}
	
	@RequestMapping("/usr/home/getFloat")
	@ResponseBody
	public float getFloat() { // 리턴타입이 float
		return 10.5f;
	}
	
	@RequestMapping("/usr/home/getDouble")
	@ResponseBody
	public double getDouble() { // 리턴타입이 double
		return 10.5; // 웹 브라우저에서도 이게 10.5라는 실수가 아닌 그냥 문자열 1 0 . 5로 인식
	}
	
	@RequestMapping("/usr/home/getBoolean")
	@ResponseBody
	public boolean getBoolean() { // 리턴타입이 boolean
		return true; // 웹 브라우저에서는 자바에서 사용하는 것을 인식할 수 없음. 그냥 문자열 t r u e로 인식함
	}
	
	@RequestMapping("/usr/home/getCharacter")
	@ResponseBody
	public char getCharacter() { // 리턴타입이 char
		return 'a';
	}
	
	@RequestMapping("/usr/home/getMap")
	@ResponseBody
	public Map<String, Object> getMap() { // 리턴타입이 HashMap
		Map<String, Object> map = new HashMap<>();
		map.put("철수나이", 22);
		map.put("영희나이", 21);
		
		return map;
	}
	
	@RequestMapping("/usr/home/getList")
	@ResponseBody
	public List<String> getList() { // 리턴타입이 ArrayList
		List<String> list = new ArrayList<>();
		list.add("철수");
		list.add("영희");
		
		return list;
	}
	
	@RequestMapping("/usr/home/getArticle")
	@ResponseBody
	public Article getArticle() { // 리턴타입이 Article 클래스
		Article article = new Article(1, "제목1", "내용1");
		// Article article = new Article(); @NoArgsConstructor이걸 쓰면 이렇게 인자 없는 생성자도 생성 가능
		
		return article;
	}
	
	@RequestMapping("/usr/home/getArticles")
	@ResponseBody
	public List<Article> getArticles() { 
		Article article1 = new Article(1, "제목1", "내용1");
		Article article2 = new Article(2, "제목2", "내용2");
		
		List<Article> list = new ArrayList<>();
		list.add(article1);
		list.add(article2);
		
		return list;
		
	}
}




/*
 * Request => 요청된것, 컴퓨터 입장에서는 받은편지
 * Response => 응답하는것, 컴퓨터 입장에서는 보낼편지
*/