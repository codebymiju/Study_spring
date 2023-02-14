package com.itwillbs.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itwillbs.test.vo.MemberVO;
import com.itwillbs.test.vo.TestVO;

@Controller
public class TestController {

	@GetMapping(value = "main")
	public String main() {
		return "main"; // Dispatch 방식
	}
	
	// -------------------------------------------------------------------------------------
//	@GetMapping(value = "push")
//	public String push(HttpServletRequest request) {
//		// HttpServletRequest 객체(내장객체명 : request)의 setAttribute() 메서드를 사용하여
//		// Dispatch 방식 포워딩 시 전달할 데이터를 저장 가능
//		// => 단, JSP 가 아니므로 내장 객체 request 가 존재하지 않음
//		//    따라서, 외부로부터 객체를 전달(= 주입) 받아야 한다! 
//		//    (=> 스프링에서 의존주입(DI = Dependency Injection) 이라고 함)
//		// => push() 메서드 내의 파라미터 타입으로 HttpServletRequest 타입 선언하면
//		//    스프링에 의해 해당 객체가 자동으로 전달(주입) 됨
//		
//		// request 객체에 "msg" 라는 속성명으로 "Hello, World!" 문자열 저장
//		request.setAttribute("msg", "Hello, World!");
//		
//		// request 객체에 "test" 라는 속성명으로 TestVO 객체 저장
//		request.setAttribute("test", new TestVO("제목", "내용"));
//		
//		// Dispatch 방식으로 "/WEB-INF/views/push.jsp" 페이지 포워딩 요청
//		// => Dispatch 방식이므로 URL 유지되고, request 객체가 유지됨
//		return "push"; // Dispatch 방식
//	}
	
	// org.springframework.ui.Model 타입을 파라미터로 지정 시
	// 데이터 저장이 가능한 Model 객체를 자동으로 전달받을 수 있음
	// => HttpServletRequest 객체와 성격이 유사하며, java.util.Map 객체 기반으로 만든
	//    스프링에서 제공하는 데이터 공유 객체
	@GetMapping(value = "push")
	public String push(Model model) {
		// request.setAttribute() 와 마찬가지로 Model 객체의 addAttribute() 로 데이터 저장
		// => request 객체와 범위(scope) 동일
		// => request 객체와 동시 사용 불가(일반적인 데이터 저장 시 request 객체보다 더 많이 사용)
		model.addAttribute("msg", "Hello World! - Model 객체");
		model.addAttribute("test", new TestVO("제목 - Model 객체", "내용 - Model 객체"));
		// => view 페이지에서는 request 객체 사용법과 동일하게 사용 가능
		//    (request.getAttribute() 로 리턴받을 수 있다!)
		return "push";
	}
	// -------------------------------------------------------------------------------------
	// 리다이렉트 방식 포워딩 처리를 수행하려면
	// return 문에 "redirect:/리다이렉트할주소" 형식으로 지정
	// => 주의! 리다이렉트 방식의 경우 request 또는 model 객체를 통해 데이터 전달 불가
//	@GetMapping(value = "redirect")
//	public String redirect() {
//		// 리다이렉트 방식으로 포워딩 할 새 서블릿 주소 "redirectServlet" 지정
//		return "redirect:/redirectServlet";
//		// => 리다이렉트 방식이므로 주소표시줄의 요청 URL 이 "redirectServlet" 으로 변경됨
//	}
//	
//	// "redirectServlet" 에 대해 Dispatch 방식으로 포워딩 수행할 redirectServlet() 메서드 정의
//	// => redirect.jsp 페이지로 포워딩
//	@GetMapping(value = "redirectServlet")
//	public String redirectServlet() {
//		// Dispatch 방식으로 포워딩 할 뷰페이지를 "redirect.jsp" 로 지정
//		return "redirect";
//		// => Dispatch 방식이므로 주소표시줄의 요청 URL 이 "redirectServlet" 으로 유지됨
//	}
	// -------------------------------------------------------------------------------------
//	@GetMapping(value = "redirect")
//	public String redirect() {
//		// 리다이렉트 방식에서 데이터 전달 방법
//		// => request 객체나 model 객체 사용 불가하므로, URL 을 통해 전달
//		String name = "hong";
//		int age = 20;
//		
//		// redirectServlet 서블릿 주소 요청 시 이름과 나이를 URL 파라미터로 전달
//		return "redirect:/redirectServlet?name=" + name + "&age=" + age;
//	}
	
	// 리다이렉트 요청 시 전달받는 파라미터 데이터를 컨트롤러 메서드 내에서 접근할 경우
	// 1) HttpServletRequest 객체를 활용한 기존 방법
//	@GetMapping(value = "redirectServlet")
//	public String redirectServlet(HttpServletRequest request) {
//		String name = request.getParameter("name");  
//		int age = Integer.parseInt(request.getParameter("age"));
//		System.out.println("이름 : " + name + ", 나이 : " + age);
//		return "redirect";
//	}
	// 2) 전달받은 파라미터명과 동일한 이름의 매개변수만 선언하면
	//    이름이 일치하는 파라미터 데이터를 자동으로 변수에 저장(= 자동 주입)(GET or POST 무관)
//	@GetMapping(value = "redirectServlet")
//	public String redirectServlet(String name, int age) {
//		System.out.println("이름1 : " + name + ", 나이1 : " + age);
//		return "redirect";
//	}
	// 3) 전달받은 파라미터명과 동일한 이름의 변수가 선언된 VO 클래스를 지정하면
	//    해당 VO 클래스 인스턴스 생성 및 Setter 호출을 통한 데이터 저장까지 자동으로 수행됨
	// => 단, 기본 생성자와 Setter 정의 필요!
//	@GetMapping(value = "redirectServlet")
//	public String redirectServlet(PersonVO p) {
//		System.out.println("이름2 : " + p.getName() + ", 나이2 : " + p.getAge());
//		return "redirect";
//	}
	
	// @RequestParam 어노테이션 테스트용 age 파라미터 삭제를 위한 redirect() 메서드 정의
	@GetMapping(value = "redirect")
	public String redirect() {
		// 리다이렉트 방식에서 데이터 전달 방법
		// => request 객체나 model 객체 사용 불가하므로, URL 을 통해 전달
		String name = "hong";
		int age = 20;
		
		// redirectServlet 서블릿 주소 요청 시 이름만 URL 파라미터로 전달(age 제외)
		return "redirect:/redirectServlet?name=" + name;
	}
	
	// 서블릿 요청 처리 메서드 정의 시 @RequestParam 어노테이션을 사용하여
	// 파라미터 데이터라는 표시를 명확하게 명시 가능
	// => 또한, defaultValue 속성을 사용하면 전달되지 않은 파라미터에 대해
	//    기본값 설정까지 가능함
	// => @RequestParam(defaultValue = "기본값") 형태로 지정
	//    (단, 모든 파라미터는 문자열 형태로 취급됨)
	@GetMapping(value = "redirectServlet")
	public String redirectServlet(
			@RequestParam(defaultValue = "") String name, 
			@RequestParam(defaultValue = "0") int age) {
		System.out.println("이름1 : " + name + ", 나이1 : " + age);
		return "redirect";
	}
	
	
	// ModelAndView 객체 : 데이터 저장 용도의 Model 객체 기능과
	//                     view 페이지 포워딩 용도의 기능을 함께 수행하는 객체
	// => ModelAndView 객체를 활용하는 메서드 정의 시 리턴타입을 ModelAndView 타입으로 지정
	@GetMapping(value = "mav")
	public ModelAndView modelAndView() {
		// ModelAndView 객체를 통해 전달할 데이터 저장용 Map 계열 객체 생성
//		Map<String, PersonVO> map = new HashMap<String, PersonVO>();
		// map 객체를 통해 저장할 데이터 지정
//		map.put("person", new PersonVO("홍길동", 20));
		
		// Map 객체에 TestVO 객체 저장
		Map<String, TestVO> map = new HashMap<String, TestVO>();
		map.put("test", new TestVO("제목입니다.", "내용입니다."));
		
		// 기존 방식을 사용하여 객체 저장 후 뷰페이지로 이동하는 방법
//		model.addAttribute("map", map);
//		return "model_and_view";
		
		// ModelAndView 객체를 사용하여 객체 저장 후 뷰페이지로 이동하는 방법
		// => 객체 생성 : new ModelAndView("이동할 페이지", "저장데이터의 키", 저장데이터);
		// => 포워딩 방식은 기존과 동일한 Dispatch 방식으로 포워딩
		return new ModelAndView("model_and_view", "map", map);
		// => "WEB-INF/views/model_and_view.jsp" 페이지로 map 객체를 "map" 이라는 키로 전달
	}
	
}














