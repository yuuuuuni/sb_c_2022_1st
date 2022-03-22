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

	public ResultData<Integer> writeArticle(int memberId, String title, String body) {
		articleRepository.writeArticle(memberId, title, body); // 게시물이 추가됨
		int id = articleRepository.getLastInsertId(); // 마지막에 추가된 게시물의 번호를 선택해서 id에 담음
		
		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id); // 리턴타입을 ResultData 형식으로 바꿈
	}

	public List<Article> getForPrintArticles(int actorId) { // 출력용 게시물들을 얻음. 나중에 게시물을 작성한 회원과 로그인한 회원이 맞는지를 비교하기 위해 actorId를 매개변수로 받아줌
		List<Article> articles = articleRepository.getForPrintArticles();
		
		for(Article article : articles) {
			updateForPrintData(actorId, article);
		}
		
		return articles;
	}

	public Article getForPrintArticle(int actorId, int id) { // 출력용 게시물을 얻음. 위와 같은 이유로 actorId 추가 (부가적인 정보를 얻기 위해 actorId가 필요)
		Article article = articleRepository.getForPrintArticle(id);
		
		updateForPrintData(actorId, article);
		
		return article;
	}

	private void updateForPrintData(int actorId, Article article) { // 로그인한 사람과 해당 게시물을 받아서 article의 extra__actorCanDelete의 값을 true 또는 false로 갱신해주는 메소드
		if(article == null) {
			return;
		}
		
		ResultData actorCanDeleteRd = actorCanDelete(actorId, article); // actorId(로그인한 회원)가 해당 article을 삭제할 수 있는지의 여부를 판가름하여 그 결과값을 actorCanDeleteRd에 담음
		article.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess()); // isSuccess 메소드의 리턴값에 따라 extra__actorCanDelete에 true 또는 false가 들어감
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public ResultData<Article> modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
		
		Article article = getForPrintArticle(0, id); // 0은 딱히 의미없음 여기서는 로그인한 사람이 필요없으므로 자리만 채워주기 위해 0 넣어준것
		
		return ResultData.from("S-1", Ut.f("%d번 게시물이 수정되었습니다.", id), "article", article);
	}

	public ResultData actorCanModify(int actorId, Article article) { // 로그인한 사람과 해당 게시물을 받아서 게시물 수정의 권한 여부를 알려주는 메소드
		if(article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
		}
		
		if(article.getMemberId() != actorId) { // 게시물의 작성자와 로그인한 회원이 같지 않으면 (actorId는 로그인한 회원)
			return ResultData.from("F-2", "수정 권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "게시물 수정이 가능합니다.");
	}
	
	public ResultData actorCanDelete(int actorId, Article article) { // 로그인한 사람과 해당 게시물을 받아서 게시물 삭제의 권한 여부를 알려주는 메소드
		if(article == null) {
			return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
		}
		
		if(article.getMemberId() != actorId) { // 게시물의 작성자와 로그인한 회원이 같지 않으면 (actorId는 로그인한 회원)
			return ResultData.from("F-2", "삭제 권한이 없습니다.");
		}
		
		return ResultData.from("S-1", "게시물 삭제가 가능합니다.");
	}
	
}