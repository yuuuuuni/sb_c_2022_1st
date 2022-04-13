package com.pyh.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.service.ArticleService;
import com.pyh.exam.demo.service.BoardService;
import com.pyh.exam.demo.util.Ut;
import com.pyh.exam.demo.vo.Article;
import com.pyh.exam.demo.vo.Board;
import com.pyh.exam.demo.vo.ResultData;
import com.pyh.exam.demo.vo.Rq;

@Controller
public class UsrArticleController {
	private ArticleService articleService; // UsrArticleController에서 ArticleService를 써야하므로 ArticleService객체 생성하고 생성자로 넣어줌
	private BoardService boardService; // 위와 같은 의미
	private Rq rq;
	
	// 생성자 (@Autowired 해주는거랑 같은 의미 - @Autowired 빼고 생성자로 바꿔줌)
	public UsrArticleController(ArticleService articleService, BoardService boardService, Rq rq) {
		this.articleService = articleService;
		this.boardService = boardService;
		this.rq = rq;
	}
	
	
	// 액션 메서드 시작
	// 게시물들 조회(게시물 리스트 페이지)
	@RequestMapping("/usr/article/list")
	public String showList(Model model, int boardId) { // JSP를 사용하려면 리턴타입을 String으로 바꾸고 @ResponseBody는 지워줘야함
		Board board = boardService.getBoardById(boardId);
		
		if(board == null) {
			return rq.historyBackJsOnView(Ut.f("%d번 게시판은 존재하지 않습니다.", boardId));
		}
		
		int articlesCount = articleService.getArticlesCount(boardId);
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId); // boardId에 따라 각각의 boardId에 해당하는 게시판만 보여주기 위해 인자로 넣어줌
		
		// JSP에서 쓰려면 이렇게 model.addAttribute로 등록을 해줘야 함. 그래야 JSP에서 ${~~}또는 ${~~.~}이런식으로 사용할 수 있음
		model.addAttribute("board", board);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);
		
		
		return "usr/article/list";
	}
	
	// 게시물(한 건) 조회(게시물 상세페이지)
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	// 게시물(한 건) 조회
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(int id) { // 리저트코드, 메세지, 데이터 등을 더욱 세부적으로 나타내기 위해 리턴타입을 ResultData로 바꿔줌
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), "article", article);
	}
	
	// 게시물 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		// 가져온 게시물이 비어있는 경우 (첫번째로, 비어있는지 부터 확인)
		if (article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		// 글 작성자의 번호와 로그인한 회원의 번호가 일치하는지 여부 (그 다음, 두번째로 확인)
		if(article.getMemberId() != rq.getLoginedMemberId()) {
			return rq.jsHistoryBack("삭제 권한이 없습니다.");
		}

		articleService.deleteArticle(id); // 게시물 삭제 메서드를 따로 만듦

		return rq.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list"); // 삭제했다는 알림창을 띄우고 이 uri로 이동해라
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id); // 로그인한 회원번호와 id를 주고 그에 해당하는 게시물을 가져옴
		
		// 가져온 게시물이 비어있는 경우 (첫번째로, 비어있는지 부터 확인)
		if (article == null) {
			return rq.historyBackJsOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		// 수정 권한을 체크하는 것을 서비스한테 넘김
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		// resultCode가 "S-"로 시작되지 않으면(즉, "F-"로 시작되면)
		if(actorCanModifyRd.isFail()) {
			return rq.historyBackJsOnView(actorCanModifyRd.getMsg()); // 이 실패 보고서 자체를 리턴해라
		}
		
		model.addAttribute("article", article); // article의 정보를 구해놓은 상태에서 수정해야하므로
		
		return "usr/article/modify";
	}
	
	// 게시물 수정
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		// 가져온 게시물이 비어있는 경우 (첫번째로, 비어있는지 부터 확인)
		if (article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		// 수정 권한을 체크하는 것을 서비스한테 넘김(로그인한 사람과 해당 게시물을 주고)
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		// resultCode가 "S-"로 시작되지 않으면(즉, "F-"로 시작되면)
		if(actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg()); // 이 실패 보고서 안에 있는 메세지를 자바스크립트로 띄워야하므로 getMsg()
		}
		
		articleService.modifyArticle(id, title, body); // 드디어 다 통과됐으므로 수정 처리 메소드 서비스로 토스
		
		return rq.jsReplace(Ut.f("%d번 글이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}
	
	@RequestMapping("/usr/article/write")
	public String showWrite(HttpServletRequest req, Model model) {
		
		return "usr/article/write";
	}
	
	// 게시물 작성
		@RequestMapping("/usr/article/doWrite")
		@ResponseBody
		public String doWrite(String title, String body, String replaceUri) { // Session 객체는 req를 통해서 얻을 수 있음
			if(Ut.empty(title)) {
				return rq.jsHistoryBack("title(을)를 입력해주세요.");
			} 
			
			if(Ut.empty(body)) {
				return rq.jsHistoryBack("body(을)를 입력해주세요.");
			}
			
			ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body); // 누가 글을 썼는지 알기 위해 loginedMemberId도 추가해줌. writeArticleRd에 담고나서 아랫줄에서 int형으로 형변환하기 싫으므로 아예 받을 때 미리 <Integer> 타입으로 받겠다는 뜻으로 써줌(=제네릭) 그럼 형변환을 안해도 됨
			int id = writeArticleRd.getData1(); // 마지막에 생성된 게시물을 가져오기 위해 해당 게시물의 번호부터 가져옴 (Data1에는 마지막에 생성된 게시물의 번호가 들어있기 때문)
			
			if(Ut.empty(replaceUri)) {
				replaceUri = Ut.f("../article/detail?id=%d", id);
			}
			
			return rq.jsReplace(Ut.f("%d번 글이 생성되었습니다.", id), replaceUri);
		}

	// 액션 메서드 끝
}
