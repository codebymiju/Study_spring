package com.itwillbs.mvc_board.mapper;

import com.itwillbs.mvc_board.vo.MemberVO;

// Service 객체에서 사용할(호출할) 메서드 형태를 추상메서드로 갖는
// Mapper 인터페이스 정의 -> DAO 대신 사용 가능
// -> MyBatis 와 연동할 xml 파일(XXXMaper.xml)과 연결되어 자동으로 사용됨
// 정의할 추상메서드의 이름은 XML 파일의 id 속성값과 같아야 한다.

public interface MemberMapper {

	// 1. 회원 가입에 필요한 insertMember() 추상메서드 정의
	// 추상메서드 자동 생성 & 호출위해 public 설정
	public int insertMember(MemberVO member); 
	

}
