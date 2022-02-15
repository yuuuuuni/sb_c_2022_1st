package com.pyh.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.service.ArticleService;
import com.pyh.exam.demo.vo.Article;

@Controller
public class UsrArticleController {
	@Autowired // 컴포넌트로 등록된 클래스(@Service, @Repository을 붙인 클래스)들은 new 쓰지 않고 @Autowired를 붙여 객체를 생성함
	private ArticleService articleService; // UsrArticleController에서 ArticleService를 써야하므로 @Autowired를 붙여 ArticleService객체 생성

	
	// 액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = articleService.writeArticle(title, body);
		
		Article article = articleService.getArticle(id);

		return article; // 만들어진 새 게시물이 화면에 보여져라
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articleService.getArticles(); // articles라는 어레이리스트를 화면에 보여줘라.(즉, 게시물들을 보여줘라)
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물이 존재하지 않습니다.";
		}

		articleService.deleteArticle(id); // 게시물 삭제 메서드를 따로 만듦

		return id + "번 게시물을 삭제하였습니다.";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물이 존재하지 않습니다.";
		}

		articleService.modifyArticle(id, title, body); // 게시물 수정 메서드를 따로 만듦

		return id + "번 게시물을 수정하였습니다.";
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticle(int id) { // 메소드의 리턴타입을 Object로 지정해줌으로써 리턴을 String과 Article 타입이 가능하도록 함
		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물이 존재하지 않습니다.";
		}

		return article;
	}

}
// 액션 메서드 끝