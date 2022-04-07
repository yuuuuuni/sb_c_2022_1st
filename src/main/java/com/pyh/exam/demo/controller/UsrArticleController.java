package com.pyh.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyh.exam.demo.service.ArticleService;
import com.pyh.exam.demo.util.Ut;
import com.pyh.exam.demo.vo.Article;
import com.pyh.exam.demo.vo.ResultData;
import com.pyh.exam.demo.vo.Rq;

@Controller
public class UsrArticleController {
	@Autowired // 컴포넌트로 등록된 클래스(@Service, @Mapper를 붙인 클래스)들은 new 쓰지 않고 @Autowired를 붙여 객체를 생성함
	private ArticleService articleService; // UsrArticleController에서 ArticleService를 써야하므로 @Autowired를 붙여 ArticleService객체 생성
	
	
	// 액션 메서드 시작
	// 게시물 작성
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) { // Session 객체는 req를 통해서 얻을 수 있음
		Rq rq = (Rq)req.getAttribute("rq"); // 인터셉터에서 만들어진 Rq객체 가져옴. rq에는 isLogined의 상태(t 또는 f)와 loginedMemberId의 값이 들어있음
		// req라는 카트에 Rq객체가 넣어져있으므로 getAttribute로 꺼내서 쓰면 됨
		
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		} 
		
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body); // 누가 글을 썼는지 알기 위해 loginedMemberId도 추가해줌. writeArticleRd에 담고나서 아랫줄에서 int형으로 형변환하기 싫으므로 아예 받을 때 미리 <Integer> 타입으로 받겠다는 뜻으로 써줌(=제네릭) 그럼 형변환을 안해도 됨
		int id = writeArticleRd.getData1(); // 마지막에 생성된 게시물을 가져오기 위해 해당 게시물의 번호부터 가져옴 (Data1에는 마지막에 생성된 게시물의 번호가 들어있기 때문)
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id); // 위에서 가져온 번호로 이 번호에 해당하는 게시물을 가져와서 article에 담아줌

		return ResultData.newData(writeArticleRd, "article", article); // newData 메소드를 이용하여 writeArticleRd에서는 resultCode와 msg만 필요하므로 writeArticleRd를 넘겨주고, data1Name과 data1을 각각 넘겨줌 (브라우저에 보여지는 최종 값은 성공/실패 코드, 메세지, 데이터이름, 데이터 이렇게 보여져야 하므로)
	}
	
	// 게시물들 조회(게시물 리스트 페이지)
	@RequestMapping("/usr/article/list")
	public String showList(HttpServletRequest req, Model model) { // JSP를 사용하려면 리턴타입을 String으로 바꾸고 @ResponseBody는 지워줘야함 
		Rq rq = (Rq)req.getAttribute("rq");
		
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId());
		
		model.addAttribute("articles", articles); // 이름이 "articles"이고 값이 articles인 속성을 추가하겠다.
		
		return "usr/article/list";
	}
	
	// 게시물(한 건) 조회(게시물 상세페이지)
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	// 게시물(한 건) 조회
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(HttpServletRequest req, int id) { // 리저트코드, 메세지, 데이터 등을 더욱 세부적으로 나타내기 위해 리턴타입을 ResultData로 바꿔줌
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), "article", article);
	}
	
	// 게시물 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		// 가져온 게시물이 비어있는 경우 (첫번째로, 비어있는지 부터 확인)
		if (article == null) {
			return Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		// 글 작성자의 번호와 로그인한 회원의 번호가 일치하는지 여부 (그 다음, 두번째로 확인)
		if(article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("삭제 권한이 없습니다.");
		}

		articleService.deleteArticle(id); // 게시물 삭제 메서드를 따로 만듦

		return Ut.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list"); // 삭제했다는 알림창을 띄우고 이 uri로 이동해라
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq)req.getAttribute("rq"); // loginedMemberId 얻으려고 가져옴
		
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
	public String doModify(HttpServletRequest req, int id, String title, String body) {		
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		// 가져온 게시물이 비어있는 경우 (첫번째로, 비어있는지 부터 확인)
		if (article == null) {
			return Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		// 수정 권한을 체크하는 것을 서비스한테 넘김(로그인한 사람과 해당 게시물을 주고)
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		// resultCode가 "S-"로 시작되지 않으면(즉, "F-"로 시작되면)
		if(actorCanModifyRd.isFail()) {
			return Ut.jsHistoryBack(actorCanModifyRd.getMsg()); // 이 실패 보고서 안에 있는 메세지를 자바스크립트로 띄워야하므로 getMsg()
		}
		
		articleService.modifyArticle(id, title, body); // 드디어 다 통과됐으므로 수정 처리 메소드 서비스로 토스
		
		return Ut.jsReplace(Ut.f("%d번 글이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}
	
	@RequestMapping("/usr/article/write")
	public String showWrite(HttpServletRequest req, Model model) {
		
		return "usr/article/write";
	}
	// 액션 메서드 끝
}
