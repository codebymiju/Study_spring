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

	// 1. 게시글 등록작업 수행
	public int registBoard(BoardVO board) {
		return mapper.insertBoard(board);
	}

	// 2. 게시글 목록 조회
	public List<BoardVO> getBoardList(String searchType, String keyword, int startRow, int listLimit) {
		return mapper.selectBoardList(searchType, keyword, startRow, listLimit);
	}

	// 3. 전체 게시물 수 조회
	public int getBoardListCount(String searchType, String keyword) {
		return mapper.selectBoardListCount(searchType, keyword);
	}

	// 4. 게시물 상세 정보 조회
	public BoardVO getBoard(int board_num) {
		return mapper.selectBoard(board_num);
	}

	// 5. 게시물 조회 수 증가 
	public void increaseReadcount(int board_num) {
		mapper.updateReadcount(board_num);
	}
	 
}
