package com.itwillbs.mvc_board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itwillbs.mvc_board.vo.MemberVO;

// Service 객체에서 사용할(호출할) 메서드 형태를 추상메서드로 갖는 Mapper 인터페이스 정의
// => DAO 클래스 대신 사용 가능
// => MyBatis 와 연동할 XML 파일(XXXMapper.xml)과 연결되어 자동으로 사용됨
// => 정의할 추상메서드의 이름은 XML 파일의 id 속성값과 같아야 한다!
// => Mapper 인터페이스도 Service 클래스에서 @Autowired 어노테이션을 적용 가능
//    (단, 인터페이스 적용하는 @Mapper 어노테이션은 없음)
public interface MemberMapper {
	// 1. 회원 가입에 필요한 insertMember() 추상메서드 정의
	// => 파라미터 : MemberVO 객체   리턴타입 : int
	public int insertMember(MemberVO member);

	// 2. BCryptPasswordEncoder 활용한 로그인에 필요한 selectPasswd() 추상메서드 정의
	// => 파라미터 : 아이디   리턴타입 : String
	public String selectPasswd(String id);

	// 3. 회원 상세정보 조회에 필요한 selectMemberInfo() 추상메서드 정의
	// => 파라미터 : 아이디  리턴타입 : MemberVO
	public MemberVO selectMemberInfo(String id);

	// 4. 회원 목록 조회에 필요한 selectMemberList() 정의
	// => 파라미터 : 없음   리턴타입 : List<MemberVO>
	public List<MemberVO> selectMemberList();

	// 5. 회원 정보 수정에 필요한 updateMemberInfo() 정의
	// => 파라미터 : MemberVO 객체, 새 패스워드    리턴타입 : int
//	public int updateMemberInfo(MemberVO member, String newPasswd);
	// => 주의! 복수개의 파라미터가 전달될 경우 각 파라미터를 XML 에서 구별하기 위해
	//    @Param 어노테이션을 사용하여 해당 파라미터의 이름을 지정해야한다!
	//    ex) @Param("member") MemberVO member, @Param("newPasswd") String newPasswd
	// => XML 내부에서 쿼리문 작성 시 객체는 선언된 파라미터명.변수명 형태로 접근해야한다!
	//    (ex. member.id)
	// => 변수는 선언된 파라미터명만으로 그대로 사용 가능(ex. newPasswd)
	public int updateMemberInfo(
			@Param("member") MemberVO member, // MemberVO 객체 이름을 "member" 로 사용 선언
			@Param("newPasswd") String newPasswd); // newPasswd 변수 이름을 "newPasswd" 로 사용 선언
	
	// 6. 회원 삭제에 필요한 deleteMember() 정의
	// => 파라미터 : MemberVO(member)   리턴타입 : int
	public int deleteMember(MemberVO member);

}













