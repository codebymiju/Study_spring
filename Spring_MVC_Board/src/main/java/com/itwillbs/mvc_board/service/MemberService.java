package com.itwillbs.mvc_board.service;

import java.util.List;

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

	// 23/01/18
	// BCryptPasswordEncoder 활용한 로그인 작업 - getPasswd()
	// -> 파라미터 : 아이디 / 리턴타입 : String
	// -> 회원 정보 수정에 필요한 비밀번호 조회 시에도 활용 가능
	public String getPasswd(String id) {
		
		return mapper.selectPasswd(id);
	}

	// 회원 상세 정보 조회 작업
	public MemberVO getMemberInfo(String id) {
		
		return mapper.selectMemberInfo(id);
	}
	
	// 회원 목록 조회 작업
	public List<MemberVO> getMemberList() {
		
		return mapper.selectMemberList();
	}

	// 회원 정보 수정 작업 
	public int modifyMemberInfo(MemberVO member, String newPasswd) {
	
		return mapper.updateMemberInfo(member, newPasswd);
	}

 
	 
}
