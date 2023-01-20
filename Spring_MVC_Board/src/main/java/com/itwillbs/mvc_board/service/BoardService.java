package com.itwillbs.mvc_board.service;

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
	 
}
