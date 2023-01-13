package com.itwillbs.mvc_board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
		
		// 2. BCryptPasswordEncoder 객체의 
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
		
	}
	
 }// MemberController
