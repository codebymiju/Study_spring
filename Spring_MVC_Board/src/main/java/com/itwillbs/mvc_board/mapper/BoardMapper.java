package com.itwillbs.mvc_board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.mvc_board.vo.BoardVO;

public interface BoardMapper {

	// 1. 게시물 등록
	// => 파라미터 : BoardVO 객체    리턴타입 : int
	int insertBoard(BoardVO board);

	// 2. 게시물 목록 조회
	// => 파라미터 : 검색타입, 검색어, 시작행번호, 목록갯수   
	// => 리턴타입 : List<BoardVO>
	// => 주의! 복수개의 파라미터 지정 시 @Param 어노테이션 필수! (파라미터명 지정)
	List<BoardVO> selectBoardList(
			@Param("searchType") String searchType, 
			@Param("keyword") String keyword, 
			@Param("startRow") int startRow, 
			@Param("listLimit") int listLimit);

	// 3. 전체 게시물 수 조회
	// => 파라미터 : 검색타입, 검색어   리턴타입 : int
	// => 주의! 복수개의 파라미터 지정 시 @Param 어노테이션 필수! (파라미터명 지정)
	int selectBoardListCount(
			@Param("searchType") String searchType, 
			@Param("keyword") String keyword);

	// 4. 게시물 상세 정보 조회
	// => 파라미터 : 글번호    리턴타입 : BoardVO
	BoardVO selectBoard(int board_num);

	// 5. 조회수 증가
	// => 파라미터 : 글번호
	void updateReadcount(int board_num);
}










