package com.pyh.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.service.ArticleService;
import com.pyh.exam.demo.util.Ut;
import com.pyh.exam.demo.vo.Article;
import com.pyh.exam.demo.vo.ResultData;

@Controller
public class UsrArticleController {
	@Autowired // 컴포넌트로 등록된 클래스(@Service, @Repository을 붙인 클래스)들은 new 쓰지 않고 @Autowired를 붙여 객체를 생성함
	private ArticleService articleService; // UsrArticleController에서 ArticleService를 써야하므로 @Autowired를 붙여 ArticleService객체 생성

	
	// 액션 메서드 시작
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(String title, String body) {
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		} 
		
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body); // writeArticleRd에는 ResultData 타입의 추가된 게시물의 result코드, 메세지, 데이터가 담겨짐
		int id = writeArticleRd.getData1();
		
		Article article = articleService.getArticle(id); // 마지막에 추가된 게시물의 번호에 해당하는 게시물을 꺼내 article에 담아라

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article); // writeArticleRd에는 추가된 게시물(result코드, 메세지, 데이터)이 담겨져있으므로 get으로 하나씩 차례로 뽑아준 후 리턴
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData getArticles() { // 리턴타입을 ResultData로 바꾸고
		List<Article> articles = articleService.getArticles(); // 가져온 게시물들을 어레이리스트 articles라는 변수를 만들어 담아줌
		
		return ResultData.from("S-1", "게시물 리스트 입니다.", articles); // ResultData 포맷에 맞게 코드, 메세지, 데이터인 articles를 리턴
		
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
	public ResultData getArticle(int id) { // 리저트코드, 메세지, 데이터 등을 더욱 세부적으로 나타내기 위해 리턴타입을 ResultData로 바꿔줌
		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), article);
	}

}
// 액션 메서드 끝