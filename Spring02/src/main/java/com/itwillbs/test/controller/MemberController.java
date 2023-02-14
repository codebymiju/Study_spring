package com.itwillbs.test.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.itwillbs.test.vo.MemberVO;

@Controller
public class MemberController {
	// login.me => login() => member_login_form.jsp
//	@RequestMapping(value = "login.me", method = RequestMethod.GET) 
	@GetMapping(value = "login.me")
	public String login() {
		return "member_login_form";
	}
	
	// 로그인 폼에서 로그인 버튼 클릭 시 "login.me" 서블릿 요청(POST 방식)
	// => 이 때, 로그인 폼을 표시하는 서블릿 주소와 비즈니스 로직 서블릿 주소가 동일하더라도
	//    요청 방식이 다르면 동일한 서블릿 주소를 구별 가능하다!
//	@PostMapping(value = "login.me")
//	public String loginPro() {
//		System.out.println("loginPro()");
//		return "";
//	}
	
	// --------------------------------------------------------------------------
	// [ 요청 파라미터 처리하는 방법 ]
	// 1. 파라미터명과 동일한 매개변수를 선언하여 자동으로 전달받는 방법
	// => 아이디(id), 패스워드(passwd) 를 전달받을 변수를 메서드 파라미터로 선언
//	@PostMapping(value = "login.me")
//	public String login(@RequestParam String id, @RequestParam String passwd) { 
//		// 메서드 오버로딩을 통해 동일한 이름의 메서드로도 정의 가능!
//		System.out.println("아이디 : " + id);
//		System.out.println("패스워드 : " + passwd);
//
//		// Redirect 방식으로 메인페이지(/main)로 포워딩
//		return "redirect:/main";
//	}
	
	// 2. Map 객체를 통해 파라미터명을 key, 파라미터값을 value 로 전달받는 방법
	// => @RequestParam 어노테이션을 사용하여 선언
//	@PostMapping(value = "login.me")
//	public String login(@RequestParam Map<String, String> params) { 
//		// 메서드 오버로딩을 통해 동일한 이름의 메서드로도 정의 가능!
//		// => 아이디와 패스워드가 "id", "passwd" 라는 키로 Map 객체에 각각 자동으로 저장됨
//		System.out.println("아이디(Map) : " + params.get("id"));
//		System.out.println("패스워드(Map) : " + params.get("passwd"));
//		
//		// Redirect 방식으로 메인페이지(/main)로 포워딩
//		return "redirect:/main";
//	}
	
	// 3. VO 객체를 통해 일치하는 멤버변수에 자동으로 전달받는 방법
	// => @ModelAttribute 어노테이션을 사용하여 VO 객체 타입 변수 선언(어노테이션 생략 가능)
//	@PostMapping(value = "login.me")
//	public String login(@ModelAttribute MemberVO member) { 
//		// 아이디와 패스워드가 MemberVO 객체의 id, passwd 변수에 각각 자동으로 저장됨
//		System.out.println("아이디(MemberVO) : " + member.getId());
//		System.out.println("패스워드(MemberVO) : " + member.getPasswd());
//		
//		// Redirect 방식으로 메인페이지(/main)로 포워딩
//		return "redirect:/main";
//	}
	// --------------------------------------------------------------------------
	// 로그인 판별을 수행하여 로그인에 성공했다고 가정하고
	// HttpSession 객체를 통해 세션에 로그인 성공한 아이디를 저장하는 방법
	// => 매핑 메서드 파라미터로 HttpSession 타입 변수 선언
	@PostMapping(value = "login.me")
	public String login(@ModelAttribute MemberVO member, HttpSession session) { 
		// 아이디와 패스워드가 MemberVO 객체의 id, passwd 변수에 각각 자동으로 저장됨
		System.out.println("아이디(MemberVO) : " + member.getId());
		System.out.println("패스워드(MemberVO) : " + member.getPasswd());
		
		// 로그인 성공 아이디를 세션에 저장
		// HttpSession session = request.getSession(); // 불필요
		session.setAttribute("sId", member.getId());
		
		// Redirect 방식으로 메인페이지(/main)로 포워딩
		return "redirect:/main";
	}
	
	// logout.me 서블릿 요청에 대해 로그아웃 작업 수행 후 메인페이지로 이동
	// => HttpSession 객체 필요
	@GetMapping(value = "logout.me")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/main";
	}
}
