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

import com.google.protobuf.Value;
import com.itwillbs.mvc_board.service.MemberService;
import com.itwillbs.mvc_board.vo.MemberVO;

@Controller
public class MemberController {
	
	// 23/01/11
	// Service 생성 없이 자동 주입 기능을 위한 어노테이션(@Autowired) 사용
	@Autowired
	private MemberService service;
	
	// "/MemberJoinForm.me" 요청에 대해 member/member_join_form.jsp 페이지 포워딩
	// -> Dispatch 방식 포워딩
	@GetMapping (value = "/MemberJoinForm.me")
	public String join() {
		return "member/member_join_form";
	}// join()
	
	// "/MemberJoinPro.me" 요청에 대해 MemberService 객체를 사용한 
	// 비즈니스 로직 수행
	// 1. post 방식 요청 & redirect
	// 2. 폼 파라미터로 전달되는 가입 정보 저장할 vo 타입 변수 선언
	// 3. 가입 완료 후 이동할 페이지 : 메인 페이지
	@PostMapping (value = "/MemberJoinPro.me" )
	public String joinPro(@ModelAttribute MemberVO member, Model model) {
		// 입력받은 패스워드는 암호화(해싱) 필요 -> 해싱 후 MemberVO 객체 패스워드에 덮어쓰기
		// -> 스프링 암호화 : BCryptPasswordEncoder 객체 필요
		// 1. BCryptPasswordEncoder 객체 생성
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		// 2. BCryptPasswordEncoder 객체의 encode() 호출하여 해싱 결과 리턴 
		String securePasswd = passwordEncoder.encode(member.getPasswd());
		System.out.println("평문 : " + member.getPasswd());
		System.out.println("암호문 : " + securePasswd);
		
		member.setPasswd(securePasswd);
		
		//-----------------------------------------------
		// MemberService 객체의 joinMember() 메서드 호출
//		MemberService service = new MemberService();
		
		// 자동 주입 되므로 객체 즉시 사용 가능
		int insertCount = service.joinMember(member);
		
		// 가입 성공/실패에 따른 포워딩 작업 수행
		if(insertCount > 0 ) { // 성공
			// 메인페이지로 포워딩(Redirect)
			return "redirect:/";
		} else { // 실패
			// 오류 메시지 출력 및 이전 페이지로 돌아가는 기능을 공통으로 수행할
			// fail_back.jsp 
			// 출력할 메세지를 해당 페이지로 전달
			// -> Model 객체를 통해 "msg" 속성명으로 "가입 실패!" 메세지 전달
			
			model.addAttribute("msg", "가입 실패!");
			return "fail_back";
			
		}
		
	}// joinPro
	
	// 23/01/17 추가 (하이퍼링크니까 GetMapping)	
	@GetMapping(value = "/MemberLoginForm.me")
	public String login() {
		
		// views 폴더에서 주소가 시작하니 폴더명/폴더 작성
		return "member/member_login_form";
	} // login
	
	// 23/01/18 추가 - 로그인
	// MemberLoginPro.me 요청에 대한 비즈니스 로직 처리 -> Post 방식
	// 파라미터 : 1. MemberVO (아이디, 패스워드 저장)
	//			  2. Model 타입 변수 (실패 시 fail_back.jsp 페이지로 메시지 전달할)
	//    		  3. Session (성공 시 세션 처리를 위해 - sId)	
	@PostMapping(value = "/MemberLoginPro.me")
	public String loginPro(@ModelAttribute MemberVO member, Model model, HttpSession session) {
		
		// 입력받은 로그인 패스워드 > 암호화(해싱) 처리 후 > MemberVO 객체 패스워드에 입력
		// 이미 암호화 되어 저장되어 있는 기존 패스워드와 암호화 된 입력 패스워드 비교
		
		// 1. BCryptPasswordEncoder 객체 생성
		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
		
		// 2. member 테이블에서 id에 해당하는 passwd 조회 후 리턴값 저장
		// @Autowired로 서비스 설정해둬서 객체 생성할 필요 없음!
		String passwd = service.getPasswd(member.getId());
		
		// 3. DB로부터 조회된 기존 패스워드 입력받은 패스워드와 비교하여 로그인 성공 여부 판별
		if(passwd == null || !passwdEncoder.matches(member.getPasswd(), passwd)) {// 입력받은 패스워드, db에 저장된 암호화된 패스워드
			// 로그인 실패
			// Model 객체에 "msg" 속성명으로 "로그인 실패!" 메시지 저장 후 -> fail_back.jsp 로 포워딩
			model.addAttribute("msg", "로그인 실패!");
			return "fail_back";
			
		} else {
			// 로그인 성공
			session.setAttribute("sId", member.getId());
			return "redirect:/"; // 루트로 이동
			
		}
		
	}// loginPro
	
	// 로그아웃
	@GetMapping(value = "/MemberLogout.me")
	public String logout(HttpSession session) {
		// 세션 초기화 
		session.invalidate();
		return "redirect:/";
		
	}// logout
	
	// "/MemberInfo.me" 요청에 대한 회원 상세정보 조회 비즈니스 로직 처리
	// -> 파라미터 : 아이디(id)
	@GetMapping(value = "/MemberInfo.me")
	public String memberInfo(String id,HttpSession session, Model model) {
		System.out.println("전달받은 아이디 : " + id);
		
		String sId = (String)session.getAttribute("sId");
		
		// 1. 세션 아이디가 없을 경우 "잘못된 접근"
		if(sId == null || sId.equals("")) {
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "fail_back";
		} else {
		// 2. 세션 아이디가 있을 경우
		// 2-(1). sId와 전달받은 아이디가 다르고, 관리자가 아니면 "권한 없음"
			
			if(id != null && !id.equals("") && !id.equals(sId) && !sId.equals("admin")) {
				model.addAttribute("msg", "권한이 없습니다!");
				return "fail_back";
			} else if(id.equals("")) {
				model.addAttribute("msg", "잘못된 접근입니다!");
				return "fail_back";
			}
			
		// 2-(2). sId와 전달받은 아이디가 같거나, 관리자면 조회 작업 수행	
			
			// service 객체의 getMemberInfo() 호출하여 회원 상세정보 조회
			// 파라미터 : 아이디   리턴타입 : MemberVO
			MemberVO member = service.getMemberInfo(id);
			
			// request와 동일하게 
			// Model 객체에 "member" 속성명으로 MemberVO 객체 저장
			model.addAttribute("member", member);
			System.out.println(member);
			
			// 회원 상세정보 페이지로 포워딩
			return "member/member_info";
		}
		
	}// memberInfo
	
	
	// "/AdminMain.me" 요청에 대한 회원목록 조회 비즈니스 로직 처리 
	@GetMapping(value = "/AdminMain.me")
	public String admin(HttpSession session, Model model) {
		// 만약, sId가 null or "" or !admin 
		// "잘못된 접근입니다!" 메세지 저장 후 fail_back.jsp 페이지로 포워딩
		String id = (String)session.getAttribute("sId");
		
		if(id == null || id.equals("") || !id.equals("admin")) {
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "fail_back";
		} else {
			List<MemberVO> memberList = service.getMemberList();
			
			// request와 동일하게 
			// Model 객체에 "memberList" 속성명으로 List 객체 저장
			model.addAttribute("memberList", memberList);
			
			// 관리자 메인페이지로 포워딩
			return "member/member_list";
		}
		
	}// memberList
	
	// "MemberUpdate.me" 요청에 대한 회원 정보 수정 폼 요청 작업
	// MemberVO = 수정 정보를 저장 / 새 패스워드 저장할 String타입
	// Model 타입 파라미터 필요 
	@PostMapping(value = "/MemberUpdate.me")
	public String memberUpdate(
			@ModelAttribute MemberVO member, 
			@RequestParam String newPasswd,
			Model model) {
		
		// 만약, 변경할 새 패스워드를 입력하지 않아도 파라미터가 존재하기에 "" 
		// 널스트링 전달됨 -> @RequestParam 의 defaultValue 속성 불필요
		System.out.println("멤버 : " + member);
		System.out.println("새 비밀번호 : " + newPasswd);
		
		// 입력받은 패스워드 확인 작업 수행 = 수정 권한이 있는지 확인
		// service.getPasswd() 재사용
		BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
		String passwd = service.getPasswd(member.getId());
		
		// 패스워드 판별
		if(passwd == null || !passwdEncoder.matches(member.getPasswd(), passwd)) {
			// 입력받은 패스워드, db에 저장된 암호화된 패스워드
			//권한이 없을 시 (입력한 비밀번호가 저장된 비밀번호와 일치하지 않을 시)
			model.addAttribute("msg", "권한이 없습니다!");
			return "fail_back";
		}
		
		// 23/01/20
		// 패스워드 확인이 성공했을 경우
		// 새 패스워드가 존재하면, 해당 패스워드도 암호화(해싱) 수행
		if(newPasswd != null && !newPasswd.equals("")) {
			newPasswd = passwdEncoder.encode(newPasswd);
		}
		
		// 패스워드 판별이 성공했을 경우
		// service 객체의 modifyMemberInfo() 호출하여 수정 작업 수행
		int updateCount = service.modifyMemberInfo(member, newPasswd);
		
		
		// 수정 성공/실패에 따른 포워딩 작업 수행
		if(updateCount > 0 ) { // 성공
			// 메인페이지로 포워딩(Redirect)
			return "redirect:/";
		} else { // 실패
			// "msg" 속성명으로 "수정 실패!" 메세지 전달 후 fail_back 포워딩
			model.addAttribute("msg", "수정 실패!");
			return "fail_back";
			
		}
		
	} // memberUpdate
		
	// 23/01/20
	// 회원 삭제 폼 -> delete()
	@GetMapping(value = "/MemberDelete.me")
	public String delete(HttpSession session, @ModelAttribute MemberVO member, Model model ) {
		
		String sId = (String)session.getAttribute("sId");
		
		// 1. 세션 아이디가 없을 경우 "잘못된 접근"
		if(sId == null || sId.equals("")) {
			model.addAttribute("msg", "잘못된 접근입니다!");
			return "fail_back";
		} else {
		// 2. 세션 아이디가 있을 경우
		// 2-(1). sId와 전달받은 아이디가 다르고, 관리자가 아니면 "권한 없음"
			
			if(member.getId() != null && !member.getId().equals("") && !member.getId().equals(sId) && !sId.equals("admin")) {
				model.addAttribute("msg", "권한이 없습니다!");
				return "fail_back";
			} else if(member.getId().equals("")) {
				model.addAttribute("msg", "잘못된 접근입니다!");
				return "fail_back";
			}
	
			// 권한이 있을 경우 삭제 폼으로 이동
			// 단, 관리자일 경우 바로 MemberDeletePro.me 서블릿 요청
			
			if(sId.equals("admin")) { // 관리자는 get방식
				return "redirect:/MemberDeletePro.me?id=" + member.getId();
			} else { // 일반회원은 post 방식이라 line.285에 get,post 가능하도록 지정
				return "member/member_delete_form";
			}
		}
	} // delete()
	
	// "MemberDeletePro.me" 요청에 대한 회원 삭제 비즈니스 로직 처리 -> deletePro()
	// get, post방식 둘 다 필요하기 때문에 method = {get, post}
//	@PostMapping(value = "/MemberDeletePro.me")
	@RequestMapping(value = "/MemberDeletePro.me", method = {RequestMethod.GET, RequestMethod.POST})
	public String deletePro(HttpSession session, @ModelAttribute MemberVO member, Model model) {
		String sId = (String)session.getAttribute("sId");
		
		if(!sId.equals("admin")) {
			
			// 입력받은 패스워드 확인 작업 수행 = 수정 권한이 있는지 확인
			// service.getPasswd() 재사용
			BCryptPasswordEncoder passwdEncoder = new BCryptPasswordEncoder();
			String passwd = service.getPasswd(member.getId());
			
			// 패스워드 판별
			if(passwd == null || !passwdEncoder.matches(member.getPasswd(), passwd)) {
				// 입력받은 패스워드, db에 저장된 암호화된 패스워드
				//권한이 없을 시 (입력한 비밀번호가 저장된 비밀번호와 일치하지 않을 시)
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
			if(sId.equals("admin")) { // 관리자
				return "redirect:/AdminMain.me";
			} else { // 일반회원
				// 세션 초기화
				session.invalidate();
				return "redirect:/";
			}
		} else { // 실패
			// "msg" 속성명으로 "삭제 실패!" 메세지 전달 후 fail_back 포워딩
			model.addAttribute("msg", "삭제 실패!");
			return "fail_back";
		}
		
	} //deletePro
	
	
		
 }// MemberController
