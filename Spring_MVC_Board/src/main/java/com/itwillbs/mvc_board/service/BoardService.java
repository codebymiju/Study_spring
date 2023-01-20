package com.itwillbs.mvc_board.service;

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
}










