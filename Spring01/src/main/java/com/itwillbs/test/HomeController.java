package com.itwillbs.test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 컨트롤러 역할을 하는 클래스 정의 시 @controller 어노테이션을 클래스 선언부 윗줄에 지정 (자동설정)
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/*
	 * Simply selects the home view to render by returning its name.
	 *  @RequestMapping 어노테이션을 사용하여 URL 매핑 작업 수행
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		// Dispatch 방식으로 views 디렉토리 내의 "home.jsp" 페이지 요청
		// -> web.xml 파일과 servlet-context.xml 파일의 설정으로 인해
		//   파일명만 지정 시 디렉토리명("WEB-INF/view")과 확장자(".jsp") 가 자동 결합됨
		return "home"; // "WEB-INF/views/home.jsp"로 이동
	}
	
	
	
	
}
