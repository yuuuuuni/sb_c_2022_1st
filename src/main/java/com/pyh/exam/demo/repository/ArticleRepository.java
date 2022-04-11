package com.pyh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pyh.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
	public void writeArticle(@Param("memberId") int memberId, @Param("title") String title, @Param("body") String body); // INSERT 쿼리는 값이 삽입되는 것이므로 리턴을 안해줌. 그러므로 타입을 void로 바꾸기
	
	@Select("""
			SELECT A.*, M.nickname AS extra__writerName
			FROM article AS A LEFT JOIN member AS M
			ON A.memberId = M.id
			WHERE A.id = #{id}
			""")
	public Article getForPrintArticle(@Param("id") int id);

	public void deleteArticle(@Param("id") int id);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);
	
	@Select("""
			<script>
			SELECT A.*, M.nickname AS extra__writerName
			FROM article AS A LEFT JOIN member AS M
			ON A.memberId = M.id
			<if test="boardId != 0">
				WHERE A.boardId = #{boardId}
			</if>
			ORDER BY A.id DESC
			</script>
			""")
	public List<Article> getForPrintArticles(@Param("boardId") int boardId);
	
	public int getLastInsertId();
	
	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS A
			<if test="boardId != 0">
				WHERE A.boardId = #{boardId}
			</if>
			</script>
			""")
	public int getArticlesCount(@Param("boardId") int boardId);
}