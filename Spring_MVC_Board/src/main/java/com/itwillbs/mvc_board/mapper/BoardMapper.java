package com.itwillbs.mvc_board.mapper;

import com.itwillbs.mvc_board.vo.BoardVO;

public interface BoardMapper {

	// 1. 게시글 등록 작업 추상메서드 선언
	int insertBoard(BoardVO board);

}
