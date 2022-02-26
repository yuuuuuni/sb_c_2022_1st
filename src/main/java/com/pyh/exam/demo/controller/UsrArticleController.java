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
	public ResultData<Article> doAdd(String title, String body) {
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		} 
		
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body); // writeArticleRd에 담고나서 아랫줄에서 int형으로 형변환하기 싫으므로 아예 받을 때 미리 <Integer> 타입으로 받겠다는 뜻으로 써줌(=제네릭) 그럼 형변환을 안해도 됨
		int id = writeArticleRd.getData1();
		
		Article article = articleService.getArticle(id); // 마지막에 추가된 게시물의 번호에 해당하는 게시물을 꺼내 article에 담아라

		return ResultData.newData(writeArticleRd, article); // newData 메소드 이용하여 writeArticleRd의 resultCode, msg는 그대로 가져가되 Data1 부분만 article로 넣어줌
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() { // 리턴타입을 ResultData로 바꾸고
		List<Article> articles = articleService.getArticles(); // 가져온 게시물들을 어레이리스트 articles라는 변수를 만들어 담아줌
		
		return ResultData.from("S-1", "게시물 리스트 입니다.", articles); // ResultData 포맷에 맞게 코드, 메세지, 데이터인 articles를 리턴
		
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(int id) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		articleService.deleteArticle(id); // 게시물 삭제 메서드를 따로 만듦

		return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제하였습니다.", id), id);
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Integer> doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		articleService.modifyArticle(id, title, body); // 게시물 수정 메서드를 따로 만듦

		return ResultData.from("S-1", Ut.f("%d번 게시물을 수정하였습니다.", id), id);
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) { // 리저트코드, 메세지, 데이터 등을 더욱 세부적으로 나타내기 위해 리턴타입을 ResultData로 바꿔줌
		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), article);
	}
	// 액션 메서드 끝
}
