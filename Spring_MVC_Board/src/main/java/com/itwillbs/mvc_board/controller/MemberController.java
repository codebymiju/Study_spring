package com.itwillbs.mvc_board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.mvc_board.service.MemberService;
import com.itwillbs.mvc_board.vo.MemberVO;

@Controller
public class MemberController {
	// 컨트롤러 클래스가 서비스 클래스에 의존적일 때
	// Service 객체를 직접 생성하지 않고, 자동 주입 기능을 위한 어노테이션 사용 가능
	// => @Inject(자바-플랫폼 공용) 또는 @Autowired(스프링 전용) 어노테이션 사용
	// => 어노테이션 지정 후 자동 주입으로 객체 생성 시 객체를 저장할 클래스 타입 변수 선언
	// => 주의! Service 클래스에 @Service 어노테이션을 필수로 지정해야한다!
	@Autowired
	private MemberService service;
	
	// "/MemberJoinForm.me" 요청에 대해 member/member_join_form.jsp 페이지 포워딩
	// => GET 방식 요청 & Dispatch 방식 포워딩
	@GetMapping(value = "/MemberJoinForm.me")
	public String join() {
		return "member/member_join_form";
	}
	
	// "/MemberJoinPro.me" 요청에 대해 MemberService 객체를 사용한 비즈니스 로직 수행
	// => POST 방식 요청 & Redirect 방식 포워딩
	// => 폼 파라미터로 전달되는 가입 정보를 저장할 VO 타입 변수 선언
	// => 가입 완료 후 이동할 페이지 : 메인페이지
	// => 가입 실패 시 오류 페이지에 메세지 전송을 위해 Model 타입 변수 선언
	@PostMapping(value = "/MemberJoinPro.me")
	public String joinPro(@ModelAttribute MemberVO member, Model model) {
		// ------------------- BCryptPasswordEncoder 객체 활용한 해싱 -------------------
		// 입력받은 패스워드는 암호화(해싱) 필요 => 해싱 후 MemberVO 객체 패스워드에 덮어쓰기
		// => 스프링에서 암호화는 BCryptPasswordEncoder 객체 사용(spring-security-web 라이브러리)
		// BCryptPasswordEncoder 클래스를 활용하여 해싱할 경우 Salting(솔팅)을 통해
		// 동일한 평문(원본 암호)이라도 매번 다른 결과값을 갖는 해싱이 가능하다!
		// 1. BCryptPasswordEncoder 객체 생성
		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
		// 2. BCryptPasswordEncoder 객체의 encode() 메서드를 호출하여 해싱 결과 리턴
		// => 파라미터 : 평문 암호    리턴타입 : String(해싱된 암호문)
		String securePasswd = passwdEncoder.encode(member.getPasswd());
//		System.out.println("평문 : " + member.getPasswd());
//		System.out.println("암호문 : " + securePasswd);
		// 3. MemberVO 객체의 패스워드에 암호문 저장(덮어쓰기)
		member.setPasswd(securePasswd);
		// ----------------------------------------------------------------------------------
		// MemberService 객체의 joinMember() 메서드 호출
		// => 파라미터 : MemberVO 객체   리턴타입 : int(insertCount)
//		MemberService service = new MemberService();
		// => 인스턴스를 직접 생성해서 사용해도 되지만
		//    @Autowired 어노테이션을 통해 MemberService 객체를 별도로 생성하지 않아도
		//    자동 주입(= 의존 주입 = DI) 되므로 객체를 즉시 사용 가능
		int insertCount = service.joinMember(member);
		
		// 가입 성공/실패에 따른 포워딩 작업 수행
		if(insertCount > 0) { // 성공
			// 메인페이지로 리다이렉트
			return "redirect:/";
		} else { // 실패
			// 오류 메시지 출력 및 이전 페이지로 돌아가는 기능을 공통으로 수행할
			// fail_back.jsp 페이지로 포워딩(Dispatch)
			// => 출력할 메세지를 해당 페이지로 전달
			// => Model 객체를 통해 "msg" 속성명으로 "가입 실패!" 메세지 전달
			model.addAttribute("msg", "가입 실패!");
			return "fail_back";
		}
		
	}
	
	@GetMapping(value = "/MemberLoginForm.me")
	public String login() {
		return "member/member_login_form";
	}
	
	// /MemberLoginPro.me 요청에 대한 로그인 비즈니스 로직 처리 => POST 방식
	// => loginPro() 메서드 정의
	// => 파라미터 : 아이디, 패스워드를 저장하기 위한 MemberVO 타입 변수 member
	//               실패 시 fail_back.jsp 페이지로 메세지 전달할 Model 타입 변수 model
	//               성공 시 세션 처리를 위한 HttpSession 타입 변수 session
	@PostMapping(value = "/MemberLoginPro.me")
	public String loginPro(@ModelAttribute MemberVO member, Model model, HttpSession session) {
		// ------------------- BCryptPasswordEncoder 객체 활용한 해싱 -------------------
		// 입력받은 로그인 패스워드는 암호화(해싱) 필요 => 해싱 후 MemberVO 객체 패스워드에 덮어쓰기
		// 이미 암호화 되어 저장되어 있는 기존 패스워드와 암호화 된 입력 패스워드를 비교해야함
		// 1. BCryptPasswordEncoder 객체 생성
		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
		
		// 2. member 테이블에서 id 에 해당하는 암호화 된 패스워드 조회하기 위한 
		//    MemberService 객체의 getPasswd() 메서드 호출
		//    => 파라미터 : 아이디   리턴타입 : String(passwd)
		String passwd = service.getPasswd(member.getId());
		
		// 3. 데이터베이스로부터 조회된 기존 패스워드(암호문)를 
		//    입력받은 패스워드(평문)와 비교하여 로그인 성공 여부 판별
		//    1) 아이디가 없을 경우(passwd 값이 null) 실패
		//    2) 아이디가 있을 경우, 패스워드 비교(BCryptPasswordEncoder 의 matches() 메서드)
		//       (=> matches(평문, 암호문))
		//       2-1) 다를 경우 실패
		//       2-2) 같을 경우 성공
		if(passwd == null || !passwdEncoder.matches(member.getPasswd(), passwd)) { // 실패
			// Model 객체에 "msg" 속성명으로 "로그인 실패!" 메세지 저장 후
			// fail_back.jsp 페이지로 포워딩
			model.addAttribute("msg", "로그인 실패!");
			return "fail_back";
		} else { // 성공
			// HttpSession 객체에 세션 아이디 저장 후 메인페이지로 리다이렉트
			session.setAttribute("sId", member.getId());
			return "redirect:/";
		}
		
	}
	
	// "/MemberLogout.me" 요청에 대한 로그아웃 비즈니스 로직 처리 => logout()
	// => 로그아웃 완료 후 메인페이지로 리다이렉트
	// => 파라미터 : HttpSession(session)  
	@GetMapping(value = "/MemberLogout.me")
	public String logout(HttpSession session) {
		// 세션 초기화
		session.invalidate();
		return "redirect:/";
	}
	
	// "/MemberInfo.me" 요청에 대한 회원 상세정보 조회 비즈니스 로직 처리 => memberInfo()
	// => 파라미터 : 아이디(id)
	@GetMapping(value = "/MemberInfo.me")
	public String memberInfo(String id, HttpSession session, Model model) {
		String sId = (String)session.getAttribute("sId");
		
		// 1. 세션 아이디가 없을 경우 "잘못된 접근"
		if(sId == null || sId.equals("")) {
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "fail_back";
		} else {
			// 2. 세션 아이디가 있을 경우
			// 2-1) 세션 아이디와 전달받은 아이디가 다르고, 관리자가 아니면 "권한없음"
			// !id.equals("")
			System.out.println("세션 아이디 있음. 아이디 : " + id);
			if(id != null && !id.equals("") && !id.equals(sId) && !sId.equals("admin")) {
				model.addAttribute("msg", "권한이 없습니다!");
				return "fail_back";
			} else if(id.equals("")) {
				model.addAttribute("msg", "잘못된 접근입니다!");
				return "fail_back";
			}
			
			// 2-2) 세션 아이디와 전달받은 아이디가 같거나, 관리자이면 조회 작업 수행
			// Service 객체의 getMemberInfo() 메서드 호출하여 회원 상세정보 조회
			// => 파라미터 : 아이디(id)   리턴타입 : MemberVO(member)
			MemberVO member = service.getMemberInfo(id);
			
			// -----------------------------------------------------------------
			// 만약, 리턴타입을 MemberVO 객체가 아닌 
			// -----------------------------------------------------------------
			
			// 조회 결과를 Model 객체에 "member" 속성명으로 저장
			model.addAttribute("member", member);
//			System.out.println(member);
			
			// member/member_info.jsp 페이지로 포워딩
			return "member/member_info";
		}
	}
	
	// 회원 정보 수정
	// => 수정 정보를 저장할 MemberVO 타입, 새 패스워드를 저장할 String 타입,
	//    Model 타입 파라미터 필요
	@PostMapping(value = "/MemberUpdate.me")
	public String memberUpdate(
			@ModelAttribute MemberVO member, // 기본 정보가 저장될 객체 파라미터
			@RequestParam String newPasswd, // 새 패스워드가 저장될 파라미터
			Model model) {
		// 만약, 변경할 새 패스워드를 입력하지 않아도 파라미터가 존재하므로 널스트링 전달됨
		// => 따라서, @RequestParam 어노테이션에 defaultValue 속성은 불필요
//		System.out.println(member);
//		System.out.println(newPasswd);
		
		// 입력받은 패스워드 확인 작업을 위해
		// Service 객체의 getPasswd() 메서드 재사용
		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
		String passwd = service.getPasswd(member.getId());
		
		// 패스워드 판별
		if(passwd == null || !passwdEncoder.matches(member.getPasswd(), passwd)) { // 실패
			model.addAttribute("msg", "권한이 없습니다!");
			return "fail_back";
		} 
		
//		System.out.println(member);
//		System.out.println(newPasswd);
		
		// 패스워드 확인이 성공했을 경우
		// 새 패스워드가 존재하면, 해당 패스워드도 암호화(해싱) 수행
		if(newPasswd != null && !newPasswd.equals("")) {
			newPasswd = passwdEncoder.encode(newPasswd);
		}
		
		// Service 객체의 modifyMemberInfo() 메서드 호출하여 수정 작업 수행
		// => 파라미터 : MemberVO 객체, 새 패스워드    리턴타입 : int(updateCount)
		int updateCount = service.modifyMemberInfo(member, newPasswd);
		
		// 수정 성공/실패에 따른 포워딩 작업 수행
		if(updateCount > 0) { // 성공
			// 메인페이지로 리다이렉트
			return "redirect:/";
		} else { // 실패
			// "msg" 속성명으로 "수정 실패!" 메세지 전달 후 fail_back 포워딩
			model.addAttribute("msg", "가입 실패!");
			return "fail_back";
		}
	}
	
	// "/MemberDelete.me" 요청에 대한 회원 삭제 폼 => delete()
	@GetMapping(value = "/MemberDelete.me")
	public String delete(HttpSession session, @ModelAttribute MemberVO member, Model model) {
		String sId = (String)session.getAttribute("sId");
		
		// 1. 세션 아이디가 없을 경우 "잘못된 접근"
		if(sId == null || sId.equals("")) {
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "fail_back";
		} else {
			// 2. 세션 아이디가 있을 경우
			// "권한없음" 판별
			if(member.getId() != null && !member.getId().equals("") && !member.getId().equals(sId) && !sId.equals("admin")) {
				model.addAttribute("msg", "권한이 없습니다!");
				return "fail_back";
			} else if(member.getId().equals("")) {
				model.addAttribute("msg", "잘못된 접근입니다!");
				return "fail_back";
			}
			
			// 권한이 있을 경우 삭제 폼으로 이동
			// 단, 관리자일 경우 바로 MemberDeletePro.me 서블릿 요청
			if(sId.equals("admin")) {
				return "redirect:/MemberDeletePro.me?id=" + member.getId(); // GET 방식
			} else {
				return "member/member_delete_form";
			}
		}
	}
	
	// "/MemberDeletePro.me" 요청에 대한 회원 삭제 비즈니스 로직 처리 => deletePro()
//	@PostMapping(value = "/MemberDeletePro.me")
	// 관리자일 경우 GET 방식 요청, 아니면 POST 방식 요청이므로 함께 처리 필요
	@RequestMapping(value = "/MemberDeletePro.me", 
					method = {RequestMethod.GET, RequestMethod.POST})
	public String deletePro(HttpSession session, @ModelAttribute MemberVO member, Model model) {
		// 세션 아이디 가져오기
		String sId = (String)session.getAttribute("sId");
		
		// 입력받은 패스워드 확인 작업을 위해 Service 객체의 getPasswd() 메서드 재사용
		// => 단, 세션 아이디가 "admin" 일 경우 패스워드 확인 작업 생략
		if(!sId.equals("admin")) {
			BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
			String passwd = service.getPasswd(member.getId());
			
			// 패스워드 판별
			if(passwd == null || !passwdEncoder.matches(member.getPasswd(), passwd)) { // 실패
				model.addAttribute("msg", "권한이 없습니다!");
				return "fail_back";
			}
		}
		
		// Service 객체의 removeMember() 메서드 호출하여 회원 삭제 요청
		// => 파라미터 : MemberVO(member)   리턴타입 : int(deleteCount)
		// => MemberVO 객체 대신 String id 타입으로 전달받거나
		//    removeMember() 메서드에 전달 시 member.getId() 로 패스워드만 전달해도 무관
		int deleteCount  = service.removeMember(member);
		
		// 삭제 성공/실패에 따른 포워딩 작업 수행
		if(deleteCount > 0) { // 성공
			// 관리자 or 일반 회원에 따라 다른 페이지로 리다이렉트
			// 관리자 회원의 경우 "AdminMain.me" 페이지로 리다이렉트
			// 일반 회원의 경우 메인페이지로 리다이렉트
			if(sId.equals("admin")) { // 세션 아이디가 "admin" 일 경우
				return "redirect:/AdminMain.me";
			} else { // 일반 회원일 경우
				// 세션 초기화
				session.invalidate();
				return "redirect:/";
			}
		} else { // 실패
			// "msg" 속성명으로 "삭제 실패!" 메세지 전달 후 fail_back 포워딩
			model.addAttribute("msg", "삭제 실패!");
			return "fail_back";
		}
		
	}
	
	
	
	// "/AdminMain.me" 요청에 대한 회원목록 조회 비즈니스 로직 처리 => admin()
	@GetMapping(value = "/AdminMain.me")
	public String admin(HttpSession session, Model model) {
		// 만약, 세션 아이디가 null 또는 널스트링 또는 "admin" 이 아니면
		// "잘못된 접근입니다!" 메세지 저장 후 fail_back.jsp 페이지로 포워딩
		String id = (String)session.getAttribute("sId");
		
		if(id == null || id.equals("") || !id.equals("admin")) {
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "fail_back";
		} else {
			// Service 객체의 getMemberList() 메서드를 호출하여 전체 회원 목록 조회
			// => 파라미터 : 없음   리턴타입 : List<MemberVO>(memberList)
			List<MemberVO> memberList = service.getMemberList();
			
			// Model 객체에 "memberList" 속성명으로 조회된 회원 목록(List 객체) 저장
			model.addAttribute("memberList", memberList);
			
			// 관리자 메인페이지(임시로 member_list.jsp) 로 포워딩
			return "member/member_list";
		}
		
	}
	
	
	
}













