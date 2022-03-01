package com.pyh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pyh.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	public void writeArticle(@Param("memberId") int memberId, @Param("title") String title, @Param("body") String body); // INSERT 쿼리는 값이 삽입되는 것이므로 리턴을 안해줌. 그러므로 타입을 void로 바꾸기

	public Article getArticle(@Param("id") int id);

	public void deleteArticle(@Param("id") int id);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	public List<Article> getArticles();
	
	public int getLastInsertId();
}