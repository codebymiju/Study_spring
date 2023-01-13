package com.itwillbs.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itwillbs.test.vo.MemberVO;
import com.itwillbs.test.vo.PersonVO;
import com.itwillbs.test.vo.TestVO;

@Controller
public class TestController {

	@GetMapping(value = "main")
	public String main() {
		
		return "main"; // Dispatch 방식
	}
	
//	@GetMapping(value = "push")
//	public String push(HttpServletRequest request) {
//		// HttpServletRequest 객체(내장객체명 : request)의 setAttribute() 메서드를 사용하여
//		// Dispatch 방식 포워딩 시 전달할 데이터를 저장 가능
//		// -> 스프링에서는 의존주입(DI : Dipendency Injection )이라고 함
//		// 메서드 내의 파라미터 타입으로 HttpServletRequest 타입 선언하면
//		//  스프링에 의해 해당 객체가 자동으로 전달(주입)됨
//		
//		request.setAttribute("msg", "Hello, World!" );
//		
//		// request 객체에 "test" 라는 속성명으로 TestVO 객체 저장
//		request.setAttribute("test", new TestVO("제목", "내용"));
//				
//		
//		return "push"; // Dispatch 방식
//	}
	
	// org.springframework.ui.model 타입 파라미터를 사용
	@GetMapping(value = "push")
	public String push(Model model) {
		// request.setAttribute() = model.addAttribute()
		// request 객체와 동시 사용 불가(일반적인 데이터 저장시 request 객체보다 더 많이 사용)
		
		model.addAttribute("msg", "Hello, World! - Model객체");
		model.addAttribute("test", new TestVO("제목 - model", "내용 - model"));
		// view 페이지에서는 request 객체 사용법과 동일하게 사용
		// request.getAttribute로 받을 수 있다
		
		return "push";
	}
	
//	// 리다이렉트 방식 포워딩 처리를 수행하려면
//	// return 문에 "redirect:/리다이렉트할 주소" 형식으로 지정
//	// ***주의! 리다이렉트 방식의 경우 request혹은 model 객체를 통해 전달 불가
//	@GetMapping(value = "redirect") // 1. 이 주소 요청을 받았는데
//	public String redirect() {
//		
//		return "redirect:/redirectServlet"; // 2. redirectServlet으로 이동하라고 매핑
//	}
	
//	//"redirectServlet" 요청에 대해 Dispatch 방식으로 포워딩을 수행할 메서드
//	@GetMapping(value = "redirectServlet") // 3. 매핑할 주소를 찾아서 이동
//	public String redirectServlet() {
//		
//		// Dispatch 방식이니 주소창이 redirectServlet으로 유지가 됨
//		// 실질적 이동은 redirect로 했음!
//		return "redirect"; // 4. 그 주소가 redirect.jsp 
//		
//		// 2,3,4가 중요
//	}
	
//	@GetMapping(value = "redirect") // 1. 이 주소 요청을 받았는데
//	public String redirect() {
//		// 리다이렉트 방식에서 데이터 전달 방법
//		// -> request 객체나 Model 객체 사용 불가하므로, URL 통해 전달
//		String name = "hong";
//		int age = 20;
//		
//		// redirectServlet 서블릿 주소 요청 시 이름과 나이를 url 파라미터로 전달
//		return "redirect:/redirectServlet?name=" + name + "&age=" + age; // 2. redirectServlet으로 이동하라고 매핑
//	}
//	
//	// "redirectServlet" 요청에 대해 Dispatch 방식으로 포워딩을 수행할 메서드
//	@GetMapping(value = "redirectServlet") // 3. 매핑할 주소를 찾아서 이동
//	public String redirectServlet(HttpServletRequest request) {
//		
//		String name = request.getParameter("name"); 
//		int age = 	Integer.parseInt(request.getParameter("age"));
//		
//		System.out.println("이름 : " + name + ", 나이 : " + age);
//		// Dispatch 방식이니 주소창이 redirectServlet으로 유지가 됨
//		// 실질적 이동은 redirect로 했음!
//		return "redirect"; // 4. 그 주소가 redirect.jsp 
//		
//		// 2,3,4가 중요
//	}
	
	// 2. 전달받는 파라미터명과 동일한 이름의 매개변수만 선언하면
	// 이름이 일치하는 파라미터 데이터를 자동으로 변수에 저장(=자동주입, get & post 상관없음)
//	@GetMapping(value = "redirectServlet") // 3. 매핑할 주소를 찾아서 이동
//	public String redirectServlet(String name, int age) {
//		
//		System.out.println("이름1 : " + name + ", 나이1 : " + age);
//		// Dispatch 방식이니 주소창이 redirectServlet으로 유지가 됨
//		// 실질적 이동은 redirect로 했음!
//		return "redirect"; // 4. 그 주소가 redirect.jsp 
//		
//		// 2,3,4가 중요
//	}
	
	// 3. 전달받은 파라미터명과 동일한 이름의 변수가 선언된 VO클래스를 지정하면
	//     해당 VO클래스 인스턴스 생성 및 Setter 호출을 통한 데이터 저장까지 자동으로 수행됨
	//     -> 단, 기본 생성자와 Setter 정의 필요
//	@GetMapping(value = "redirectServlet") // 3. 매핑할 주소를 찾아서 이동
//	public String redirectServlet(PersonVO p) {
//		
//		System.out.println("이름2 : " + p.getName() + ", 나이2 : " + p.getAge());
//		// Dispatch 방식이니 주소창이 redirectServlet으로 유지가 됨
//		// 실질적 이동은 redirect로 했음!
//		return "redirect"; // 4. 그 주소가 redirect.jsp 
//		
//		// 2,3,4가 중요
//	}
	
	
	// @RequestParam 어노테이션 테스트용 age 파라미터 삭제를 위한 redirect() 정의
	@GetMapping(value = "redirect") // 1. 이 주소 요청을 받았는데
	public String redirect() {
		// 리다이렉트 방식에서 데이터 전달 방법
		// -> request 객체나 Model 객체 사용 불가하므로, URL 통해 전달
		String name = "hong";
		int age = 20;
		
		// redirectServlet 서블릿 주소 요청 시 이름과 나이를 url 파라미터로 전달
		return "redirect:/redirectServlet?name=" + name; // 2. redirectServlet으로 이동하라고 매핑
	}
	
	// 서블릿 요청 처리 메서드 정의시 @RequestParam 사용하여 
	// 파라미터 데이터라는 표시를 명확하게 명시 가능
	// @RequestParam(defaultValue = "기본값") 형태로 지정
	@GetMapping(value = "redirectServlet") // 3. 매핑할 주소를 찾아서 이동
	public String redirectServlet(
			@RequestParam(defaultValue = "") String name, 
			@RequestParam(defaultValue = "0")int age) {
		
		System.out.println("이름1 : " + name + ", 나이1 : " + age);
		return "redirect"; // 4. 그 주소가 redirect.jsp 
		
		// 2,3,4가 중요
	}
	
	
	// 23/01/09 model_and_view
	// ModelAndView 객체 : 데이터 저장 용도의 Model 객체 기능 &
//							view페이지 포워딩 용도의 기능을 함께 수행하는 객체
	@GetMapping(value = "mav")
	public ModelAndView modelAndView() {
		// ModelAndView 객체를 통해 전달할 데이터 저장용 Map 계열 객체 생성
		// map <이름, 객체>
		Map<String, PersonVO> map = new HashMap<String, PersonVO>();
		// map 객체를 통해 저장할 데이터 지정
		map.put("person", new PersonVO("홍길동", 20));
		
		// 기존 방식을 사용하여 객체 저장 후 뷰페이지로 이동하는 방법
//		 model.addAttribute("map", map);
//		 return "model_and_view";
		
		// (새로운) ModelAndView 객체를 사용하여 객체 저장 후 뷰페이지로 이동하는 방법
		// 객체 생성 : new ModelAndView("이동할 페이지", "저장데이터의 키", 저장데이터);
		// 포워딩 방식 : 기존과 동일한 dispatch
		
		// map 객체에 TestVo 객체 저장
		Map<String, TestVO> map2 = new HashMap<String, TestVO>();
		map2.put("test", new TestVO("나는 제목", "나는 내용"));
		
		return new ModelAndView("model_and_view", "map2", map2);  
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
