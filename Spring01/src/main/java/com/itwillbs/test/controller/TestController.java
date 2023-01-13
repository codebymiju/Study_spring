package com.itwillbs.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 컨트롤러 역할을 하는 클래스 정의 시 @Controller 를 클래스 선언부 윗줄에 지정
@Controller
public class TestController {
	
	// "main" 서블릿 주소 요청 시 자동으로 호출되는 requestMain() 메서드 정의
	// @RequestMapping 사용하여 "GET"방식의 "main" 서블릿 주소 요청 받기
	@RequestMapping(value = "main", method = RequestMethod.GET)
	 public String requestMain() {
		// Dispatch 방식으로 views 디렉토리 내의 "index.jsp" 페이지 요청
		
		
		return "index";    
	 }
	
	@RequestMapping(value = "main2", method = RequestMethod.GET)
	public String requestMain2() {
		System.out.println("main2로 가는 요청");
		
		return "main";
	}
	
	// @GetMapping 통해 "Get"방식
	@GetMapping(value = "test1") 
	public String test1() {
		
		// views 디렉토리 내에 서브 디렉토리 사용 시
		// return "서브디렉토리명/파일명";
		
		return "test/test1";
	}
	
	@PostMapping(value = "test2")
	public String test2() {
		
		return "test/test2";
	}
	
}
