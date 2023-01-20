package com.itwillbs.mvc_board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.mvc_board.vo.MemberVO;

// Service 객체에서 사용할(호출할) 메서드 형태를 추상메서드로 갖는
// Mapper 인터페이스 정의 -> DAO 대신 사용 가능
// -> MyBatis 와 연동할 xml 파일(XXXMaper.xml)과 연결되어 자동으로 사용됨
// 정의할 추상메서드의 이름은 XML 파일의 id 속성값과 같아야 한다.

public interface MemberMapper {

	// 1. 회원 가입에 필요한 insertMember() 추상메서드 정의
	// 추상메서드 자동 생성 & 호출위해 public 설정
	public int insertMember(MemberVO member);

	// 23/01/18
	// interface에서 메서드 형태만 유지하기 위함
	// mapper 클래스와 연결해서 작업 실행할테니 (틀만 만들고, 구현은 xml에서)
	
	// 2. 로그인에 필요한 selectPasswd() 추상메서드 정의
	// BCryptPasswordEncoder 활용
	public String selectPasswd(String id);

	// 3. 회원 상세정보 조회에 필요한 추상메서드 정의
	public MemberVO selectMemberInfo(String id);

	// 4. 회원 목록 조회에 필요한 추상메서드 정의
	public List<MemberVO> selectMemberList();

	// 5. 회원 정보 수정에 필요한 추상메서드 정의
	// -> 주의! 복수개의 파라미터가 전달될 경우 각 파라미터를 XML에서 구별하기 
	//    위해 @Param 사용하여 해당 파라미터의 이름을 지정해야한다!
	// -> xml 내부에서 쿼리문 작성 시 선언된 "파라미터명.변수명" 형태로 접근해야한다!
	//     ex) member.id
	// -> 변수는 선언된 파라미터명만으로 그대로 사용가능 (ex.newPasswd)
//	public int updateMemberInfo(MemberVO member, String newPasswd); // 틀린 예
	public int updateMemberInfo(
			@Param("member") MemberVO member, // MemberVO 객체 이름을 "member"로 사용 선언
			@Param("newPasswd") String newPasswd); // newPasswd 변수 이름을 "newPasswd"로 사용 선언
	// 파라미터가 두개 이상일때는 @Param으로 이름을 지정하는 용도로 사용

	// 23/01/20
	// 6. 회원 삭제에 필요한 추상메서드 정의
	public int deleteMember(MemberVO member);

}
