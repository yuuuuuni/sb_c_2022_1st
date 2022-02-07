package com.pyh.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.vo.Article;

@Controller
public class UsrArticleController {
	private int articlesLastId;
	private List<Article> articles; // 먼저, ArrayList<Article>의 변수 articles 선언 
	
	public UsrArticleController() {
		articlesLastId = 0;
		articles = new ArrayList<>(); // 여기서 ArrayList<> 객체 생성
	}
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = articlesLastId + 1;
		Article article = new Article(id, title, body);
		
		articles.add(article);
		articlesLastId = id;
				
		return article;
	}
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articles;
	}
}

