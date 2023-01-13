package com.itwillbs.mvc_board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.mvc_board.mapper.MemberMapper;
import com.itwillbs.mvc_board.vo.MemberVO;

@Service
public class MemberService {
	// MyBatis 를 통해 SQL 구문 처리를 담당할 XXXMapper.xml 파일과
	// 연동되는 객체 자동 주입 설정
	@Autowired
	private MemberMapper mapper;
	
	// 회원 가입 joinMember() 메서드
	// -> 파라미터 : MemberVO 객체, 리턴타입 : int
	public int joinMember(MemberVO member) {
		// Mapper 객체의 메서드를 호출하여 SQL 구문 실행 요청(DAO 객체 없이 실행)
		
		return mapper.insertMember(member);
	}
 
	 
}
