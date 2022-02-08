package com.pyh.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.vo.Article;

@Controller
public class UsrArticleController {
	// 인스턴스 변수 시작
	private int articlesLastId;
	private List<Article> articles; // 게시물을 계속 추가해야 하므로 게시물들을 담을 어레이리스트 사용, 이 때 articles라는 변수 먼저 선언
	// 인스턴스 변수 끝
	
	// 생성자
	public UsrArticleController() {
		articlesLastId = 0;
		articles = new ArrayList<>(); // 여기서 어레이리스트 객체 생성

		makeTestData();
	}
	
	// 서비스 메서드 시작
	private void makeTestData() { // 테스트 게시물 생성
		for (int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;

			writeArticle(title, body);
		}
	}

	public Article writeArticle(String title, String body) {
		int id = articlesLastId + 1;
		Article article = new Article(id, title, body); // 게시물 객체 생성

		articles.add(article);
		articlesLastId = id;

		return article;
	}
	
	private Article getArticle(int id) { // 요청 들어온 id 값의 게시물을 가져오는 메소드
		for(Article article : articles) {
			if(article.getId() == id) { // article에서 id를 꺼낸게 요청 들어온 id값과 같다면
				return article; // 그 선택된 article을 우리를 호출한 getArticle 메서드로 보내줘라
			}
		}
		return null; // 요청 들어온 id 값의 게시물이 없으면 null 리턴
	}
	
	private void deleteArticle(int id) {
		Article article = getArticle(id);
		
		articles.remove(article);
	}
	// 서비스 메서드 끝
	
	// 액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle(title, body);

		return article; // 만들어진 새 게시물이 화면에 보여져라
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles; // articles라는 어레이리스트를 화면에 보여줘라.(즉, 게시물들을 보여줘라)
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = getArticle(id);
		
		if(article == null) {
			return id + "번 게시물이 존재하지 않습니다.";
		}
		
		deleteArticle(id); // 게시물 삭제 메서드를 따로 만듦

		return id + "번 게시물을 삭제하였습니다.";
	}
	// 액션 메서드 끝
}
