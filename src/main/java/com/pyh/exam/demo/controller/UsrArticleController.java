package com.pyh.exam.demo.controller;

import java.util.ArrayList;
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
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}
		
		ResultData writeArticleRd = articleService.writeArticle(title, body); // writeArticleRd에는 게시물이 추가된 후, ResultData 타입에 따라 result코드, 메세지, 데이터가 담겨짐
		int id = (int)writeArticleRd.getData1(); // writeArticleRd의 Data1(생성된 게시물 데이터)를 꺼내 int로 형변환 후 id에 담음
		
		Article article = articleService.getArticle(id); // 그 번호에 해당하는 게시물을 꺼내 article에 담아라

		return ResultData.from(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), article); // 만들어진 새 게시물이 화면에 보여져라
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
	public ResultData getArticle(int id) { // 리저트코드, 메세지, 데이터 등을 더욱 세부적으로 나타내기 위해 리턴타입을 ResultData로 바꿔줌
		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), article);
	}

}
// 액션 메서드 끝