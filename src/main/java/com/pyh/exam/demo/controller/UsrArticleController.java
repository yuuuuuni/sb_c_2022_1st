package com.pyh.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	// 게시물 작성
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {
		boolean isLogined = false; // 로그인을 안했다고 가정
		int loginedMemberId = 0; // 로그인을 안했다고 가정
		
		// 로그인 했는지 체크
		if(httpSession.getAttribute("loginedMemberId") != null) { // loginedMemberId 안에 로그인한 회원의 id가 들어있다는 뜻
			isLogined = true; // 로그인한 상태로 하겠다
			loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		if(isLogined == false) { // 로그인이 안되어있으면
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		} 
		
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body); // 누가 글을 썼는지 알기 위해 loginedMemberId도 추가해줌. writeArticleRd에 담고나서 아랫줄에서 int형으로 형변환하기 싫으므로 아예 받을 때 미리 <Integer> 타입으로 받겠다는 뜻으로 써줌(=제네릭) 그럼 형변환을 안해도 됨
		int id = writeArticleRd.getData1(); // 마지막에 생성된 게시물을 가져오기 위해 해당 게시물의 번호부터 가져옴 (Data1에는 마지막에 생성된 게시물의 번호가 들어있기 때문)
		
		Article article = articleService.getForPrintArticle(loginedMemberId, id); // 위에서 가져온 번호로 이 번호에 해당하는 게시물을 가져와서 article에 담아줌

		return ResultData.newData(writeArticleRd, "article", article); // newData 메소드를 이용하여 writeArticleRd에서는 resultCode와 msg만 필요하므로 writeArticleRd를 넘겨주고, data1Name과 data1을 각각 넘겨줌 (브라우저에 보여지는 최종 값은 성공/실패 코드, 메세지, 데이터이름, 데이터 이렇게 보여져야 하므로)
	}
	
	// 게시물들 조회(게시물 리스트 페이지)
	@RequestMapping("/usr/article/list")
	public String showList(HttpSession httpSession, Model model) { // JSP를 사용하려면 리턴타입을 String으로 바꾸고 @ResponseBody는 지워줘야함 
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		// 로그인 했는지 체크 여부
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		List<Article> articles = articleService.getForPrintArticles(loginedMemberId);
		
		model.addAttribute("articles", articles); // 이름이 "articles"이고 값이 articles인 속성을 추가하겠다.
		
		return "usr/article/list";
	}
	
	// 게시물(한 건) 조회(게시물 상세페이지)
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpSession httpSession, Model model, int id) {
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		// 로그인 했는지 체크 여부
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		Article article = articleService.getForPrintArticle(loginedMemberId, id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	// 게시물(한 건) 조회
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(HttpSession httpSession, int id) { // 리저트코드, 메세지, 데이터 등을 더욱 세부적으로 나타내기 위해 리턴타입을 ResultData로 바꿔줌
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		// 로그인 했는지 체크 여부
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), "article", article);
	}
	
	// 게시물 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpSession, int id) {
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		// 로그인 했는지 체크 여부
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		if(isLogined == false) { // 로그인이 안되어있으면
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getForPrintArticle(loginedMemberId, id);
		
		// 가져온 게시물이 비어있는 경우 (첫번째로, 비어있는지 부터 확인)
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		// 글 작성자의 번호와 로그인한 회원의 번호가 일치하는지 여부 (그 다음, 두번째로 확인)
		if(article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", "삭제 권한이 없습니다.");
		}

		articleService.deleteArticle(id); // 게시물 삭제 메서드를 따로 만듦

		return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제하였습니다.", id), "id", id);
	}
	
	// 게시물 수정
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		// 로그인 했는지 체크 여부
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		if(isLogined == false) { // 로그인이 안되어있으면
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article = articleService.getForPrintArticle(loginedMemberId, id);
		
		// 가져온 게시물이 비어있는 경우 (첫번째로, 비어있는지 부터 확인)
		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		// 수정 권한을 체크하는 것을 서비스한테 넘김
		ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);
		
		// resultCode가 "S-"로 시작되지 않으면(즉, "F-"로 시작되면)
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd; // 이 실패 보고서 자체를 리턴해라
		}
		
		return articleService.modifyArticle(id, title, body); // 게시물 수정 메서드를 따로 만듦
	}
	// 액션 메서드 끝
}
