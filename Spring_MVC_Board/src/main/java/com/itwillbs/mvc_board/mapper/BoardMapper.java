package com.itwillbs.mvc_board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.mvc_board.vo.BoardVO;

public interface BoardMapper {

	// 1. 게시글 등록 작업 추상메서드 선언
	int insertBoard(BoardVO board);

	// 23/01/25
	// 2. 게시물 목록 조회 추상메서드 선언
	// -> 주의! 복수개의 파라미터 지정 시 @Param 필수!(파라미터명 지정)
	List<BoardVO> selectBoardList(
			@Param("searchType") String searchType, 
			@Param("keyword") String keyword, 
			@Param("startRow") int startRow, 
			@Param("listLimit")	int listLimit);

	// 3. 전체 게시물 수 조회 추상메서드 선언
	int selectBoardListCount(
			@Param("searchType") String searchType, 
			@Param("keyword") String keyword);

	// 4. 게시물 상세 정보 조회
	BoardVO selectBoard(int board_num);

	// 5. 게시물 조회 수 증가
	void updateReadcount(int board_num);

}
