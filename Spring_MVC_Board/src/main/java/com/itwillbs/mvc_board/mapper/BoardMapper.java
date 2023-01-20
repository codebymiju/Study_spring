package com.itwillbs.mvc_board.mapper;

import com.itwillbs.mvc_board.vo.BoardVO;

public interface BoardMapper {

	// 1. 게시물 등록
	// => 파라미터 : BoardVO 객체    리턴타입 : int
	int insertBoard(BoardVO board);
	
}
