package com.pyh.exam.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyh.exam.demo.repository.ArticleRepository;
import com.pyh.exam.demo.vo.Article;

@Service // 스프링에게 해당 클래스가 Service라는 것을 알려주기 위해 @Service 어노테이션 붙임
public class ArticleService {
	private ArticleRepository articleRepository; // @Autowired를 빼고 빈 객체만 선언
	
	// 생성자
	public ArticleService(ArticleRepository articleRepository) { // ArticleRepository 객체를 주입식으로 투입
		this.articleRepository = articleRepository;
	}

	public int writeArticle(String title, String body) {
		articleRepository.writeArticle(title, body); // 게시물을 추가하는 메소드를 먼저 호출한 후,
		return articleRepository.getLastInsertId(); // 가장 마지막에 추가된 게시물의 id를 구하는 메서드 호출 후 리턴
	}

	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
		
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
		
	}
	
}