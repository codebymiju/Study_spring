package com.itwillbs.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.itwillbs.test.vo.BoardVO;

@Controller
public class BoardController {
	// write.bo => write() => qna_board_write.jsp
	@GetMapping(value = "write.bo")
	public String write() {
		return "qna_board_write";
	}
	
	// 글쓰기 폼에서 글쓰기 버튼 클릭 시 "write.bo" 서블릿 요청에 대한 매핑 => POST
	// => 전달되는 파라미터 가져와서 출력
	// => 작업 완료 후 Redirect 방식으로 "list.bo" 서블릿 요청
	@PostMapping(value = "write.bo")
	public String write(@ModelAttribute BoardVO board) {
		System.out.println(board.toString());
		
		return "redirect:/list.bo";
	}
	
	// 글쓰기 작업 완료 후 요청되는 list.bo(Redirect 방식)에 대한 매핑 => GET
	// => 글목록 가져오는 작업 생략하고 Dispatch 방식으로 "qna_board_list.jsp" 요청
	@GetMapping(value = "list.bo") 
	public String list(Model model) { // 데이터 저장에 사용될 Model 타입 변수 선언
		// 데이터베이스 글목록 조회 비즈니스 로직 수행했다고 가정하고 임의로 데이터 저장
		// 1. 전체 글 목록 저장하기 위한 List 객체 필요(boardList) => 제네릭타입 BoardVO
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		// 2. 1개 게시물 정보를 저장하는 BoardVO 객체 생성 후 List 객체에 추가
		boardList.add(new BoardVO(1, "관리자", "1234", "제목1", "내용1", "", "", 1, 0, 0, 0, null));
		boardList.add(new BoardVO(2, "관리자", "1234", "제목2", "내용2", "", "", 2, 0, 0, 0, null));
		boardList.add(new BoardVO(3, "관리자", "1234", "제목3", "내용3", "", "", 3, 0, 0, 0, null));
		
		// 3. 전체 글 목록이 저장된 List 객체를 뷰페이지로 전달하기 위해 Model 객체에 추가
		// => list() 메서드 파라미터에 Model 타입 선언 필요
		model.addAttribute("boardList", boardList);
		
		return "qna_board_list";
	}
	
}











