package com.pyh.exam.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pyh.exam.demo.vo.Article;

@Service // 스프링에게 해당 클래스가 Service라는 것을 알려주기 위해 @Service 어노테이션 붙임
public class ArticleService {
		private int articlesLastId; // articlesLastId는 외부에 공개하는 변수가 아니므로 private 
		private List<Article> articles; //리스트도 외부에서 건들일이 없으므로 private. 게시물을 계속 추가해야 하므로 게시물들을 담을 어레이리스트 사용, 이 때 articles라는 변수 먼저 선언
		
		// 생성자
		public ArticleService() {
			articlesLastId = 0;
			articles = new ArrayList<>(); // 여기서 어레이리스트 객체 생성

			makeTestData();
		}
		
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

		public Article getArticle(int id) { // 요청 들어온 id 값의 게시물을 가져오는 메소드
			for (Article article : articles) {
				if (article.getId() == id) { // article에서 id를 꺼낸게 요청 들어온 id값과 같다면
					return article; // 그 선택된 article을 우리를 호출한 getArticle 메서드로 보내줘라
				}
			}
			return null; // 요청 들어온 id 값의 게시물이 없으면 null 리턴
		}

		public void deleteArticle(int id) {
			Article article = getArticle(id);

			articles.remove(article);
		}

		public void modifyArticle(int id, String title, String body) {
			Article article = getArticle(id);

			article.setTitle(title); // 선택된 게시물의 title값을 title 자리에 들어오는 값으로 변경하겠다.
			article.setBody(body); // 선택된 게시물의 body값을 body 자리에 들어오는 값으로 변경하겠다. (set()은 값을 변경한다는 의미)

		}

		public List<Article> getArticles() {
			return articles;
		}
}
