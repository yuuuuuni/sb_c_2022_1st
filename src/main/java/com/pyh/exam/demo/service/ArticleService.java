package com.pyh.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pyh.exam.demo.repository.ArticleRepository;
import com.pyh.exam.demo.util.Ut;
import com.pyh.exam.demo.vo.Article;
import com.pyh.exam.demo.vo.ResultData;

@Service // 스프링에게 해당 클래스가 Service라는 것을 알려주기 위해 @Service 어노테이션 붙임
public class ArticleService {
	private ArticleRepository articleRepository; // @Autowired를 빼고 빈 객체만 선언
	
	// 생성자
	public ArticleService(ArticleRepository articleRepository) { // ArticleRepository 객체를 주입식으로 투입
		this.articleRepository = articleRepository;
	}

	public ResultData<Integer> writeArticle(int MemberId, String title, String body) {
		articleRepository.writeArticle(MemberId, title, body); // 게시물이 추가됨
		int id = articleRepository.getLastInsertId(); // 마지막에 추가된 게시물의 번호를 선택해서 id에 담음
		
		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), id); // 리턴타입을 ResultData 형식으로 바꿈
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