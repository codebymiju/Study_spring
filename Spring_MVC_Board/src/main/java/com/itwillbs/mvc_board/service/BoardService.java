package com.itwillbs.mvc_board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.mvc_board.mapper.BoardMapper;
import com.itwillbs.mvc_board.vo.BoardVO;

@Service
public class BoardService {
	@Autowired
	private BoardMapper mapper;

	// 게시물 등록 작업 수행
	// => 파라미터 : BoardVO 객체    리턴타입 : int(insertCount)
	public int registBoard(BoardVO board) {
		return mapper.insertBoard(board);
	}

	// 게시물 목록 조회
	// => 파라미터 : 검색타입, 검색어, 시작행번호, 목록갯수   
	// => 리턴타입 : List<BoardVO>(boardList)
	public List<BoardVO> getBoardList(String searchType, String keyword, int startRow, int listLimit) {
		return mapper.selectBoardList(searchType, keyword, startRow, listLimit);
	}

	// 전체 게시물 수 조회
	// => 파라미터 : 검색타입, 검색어   리턴타입 : int(listCount)
	public int getBoardListCount(String searchType, String keyword) {
		return mapper.selectBoardListCount(searchType, keyword);
	}

	// 게시물 상세 정보 조회
	// => 파라미터 : 글번호    리턴타입 : BoardVO(board)
	public BoardVO getBoard(int board_num) {
		return mapper.selectBoard(board_num);
	}

	// 조회수 증가
	// => 파라미터 : 글번호
	public void increaseReadcount(int board_num) {
		mapper.updateReadcount(board_num);
	}
	
}











