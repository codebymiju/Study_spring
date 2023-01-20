package com.itwillbs.mvc_board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.mvc_board.mapper.MemberMapper;
import com.itwillbs.mvc_board.vo.MemberVO;

// 서비스 클래스 역할을 수행하기 위한 클래스는 @Service 어노테이션을 지정
@Service
public class MemberService {
	// MyBatis 를 통해 SQL 구문 처리를 담당할 
	// XXXMapper.xml 파일과 연동되는 XXXMapper 객체 자동 주입 설정
	@Autowired
	private MemberMapper mapper;
	
	// 회원 가입 joinMember() 메서드
	// => 파라미터 : MemberVO 객체   리턴타입 : int
	public int joinMember(MemberVO member) {
		// Mapper 객체의 메서드를 호출하여 SQL 구문 실행 요청(DAO 객체 없이 실행)
		// => Mapper 객체의 메서드 실행 후 리턴되는 값을 직접 return 문에 사용하도록
		//    메서드 호출 코드 자체를 return 문 뒤에 바로 기술(리턴값이 없을 경우 호출만 수행)
		// => 단, 메서드 호출 후에도 추가 작업이 필요한 경우 호출문과 리턴문을 분리
		return mapper.insertMember(member);
	}

	// BCryptPasswordEncoder 활용한 로그인 작업 - getPasswd()
	// => 파라미터 : 아이디   리턴타입 : String
	// => 회원 정보 수정에 필요한 패스워드 조회 시 재사용
	public String getPasswd(String id) {
		return mapper.selectPasswd(id);
	}

	// 회원 상세 정보 조회 작업 - getMemberInfo()
	// => 파라미터 : 아이디  리턴타입 : MemberVO
	public MemberVO getMemberInfo(String id) {
		return mapper.selectMemberInfo(id);
	}

	// 회원 목록 조회 작업 - getMemberList()
	// => 파라미터 : 없음  리턴타입 : List<MemberVO>
	public List<MemberVO> getMemberList() {
		return mapper.selectMemberList();
	}

	// 회원 정보 수정 작업 - modifyMemberInfo()
	// => 파라미터 : MemberVO 객체, 새 패스워드    리턴타입 : int(updateCount)
	public int modifyMemberInfo(MemberVO member, String newPasswd) {
		return mapper.updateMemberInfo(member, newPasswd);
	}

	// 회원 삭제 작업 - removeMember()
	// => 파라미터 : MemberVO(member)   리턴타입 : int(deleteCount)
	public int removeMember(MemberVO member) {
		return mapper.deleteMember(member);
	}

}















