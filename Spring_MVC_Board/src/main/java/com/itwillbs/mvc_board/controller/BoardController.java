package com.itwillbs.mvc_board.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.mvc_board.service.BoardService;
import com.itwillbs.mvc_board.vo.BoardVO;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// 1. MVC 게시판 > "글쓰기" > 글쓰기 폼으로 이동
	@GetMapping(value = "/BoardWriteForm.bo")
	public String write() {
		return "board/qna_board_write";
	} // write()
	
	// 2. 글쓰기 비즈니스 로직 수행 - 파일 업로드 기능 추가
	// 파일 업로드 기능 추가 위하여 -> commons-io, commons-fileupload 라이브러리 추가
	// servlet-context.xml에 CommonsMultipartResolver 객체 설정 추가
	@PostMapping (value = "/BoardWritePro.bo")
	public String writePro(@ModelAttribute BoardVO board, Model model, HttpSession session) {
		// 세션 아이디가 존재하지 않으면 "로그인 필수!" 출력하고 이전페이지로 이동시키기
		String sId = (String)session.getAttribute("sId");
		
		if(sId == null || sId.equals("")) {
			model.addAttribute("msg", "로그인 필수!");
			return "fail_back";
		}
		
		//----------------------------------------------------------------------------
		// 주의! 파일 업로드 기능을 통해 전달받은 객체를 다룰 때
		// MultipartFile 타입 클래스 활용할 경우
		// BoardVO 클래스 내에 MultipartFile 타입 변수와 Getter/Setter 정의 필수
		// -> 이 때, 변수명은 input type="file" 태그의 name 속성과 동일한 변수명 사용
		//----------------------------------------------------------------------------
		// 1. 가상 업로드 경로에 대한 실제 업로드 경로 알아내기
		// -> request.getServletContext() 대신 session객체로 동일한 작업 수행
		String uploadDir = "/resources/upload"; // (루트 webapp폴더 기준)가상의 업로드 경로
		// -> webapp 디렉토리 내의 resources 디렉토리에 upload 디렉토리 생성 필요
		String saveDir = session.getServletContext().getRealPath(uploadDir);
//		System.out.println("실제 업로드 경로 : " + saveDir);
		// 실제 업로드 경로 : D:\workspace_sts\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Spring_MVC_Board\resources|upload

		// 실제 경로를 갖는 File 객체 생성
		File f = new File(saveDir);
		// 만약, 해당 경로 상에 실제 디렉토리가 존재하지 않을 경우 새로 생성
		if(!f.exists()) {
//			f.mkdir(); // 최종 경로가 존재하지 않으면 생성
			f.mkdirs();  // 지정된 경로 상에 존재하지 않는 모든 경로를 차례대로 생성
		}
		
		// BoardVO 객체에 전달된 MultipartFile 객체 꺼내기
		// -> 단, 복수개의 파라미터가 동일한 name 속성으로 전달된 경우 배열 타입으로 처리하기
		MultipartFile[] mFiles = board.getFiles();
		// 만약, 단일 파일일 경우
//		MultipartFile mFile = board.getfile();
		
		// 복수개의 파일을 하나의 이름으로 묶어서 다룰 경우 사용할 변수 선언
		String originalFileNames = "";
		String realFileNames = "";
		
		// 복수개의 파일에 접근하기 위한 반복문
		for(MultipartFile mFile : mFiles) {
			// MultipartFile 객체의 getOriginalFilename() 통해 파일명 꺼내기
			String originalFileName = mFile.getOriginalFilename();
//			long fileSize = mFile.getSize();
			System.out.println("원본 파일명 : " + originalFileName);
//			System.out.println("파일크기 : " + fileSize + " Byte");
			
			// 파일명 중복 방지 대책
			// 시스템에서 랜덤ID 값을 추출하여 파일명 앞에 붙이기("랜덤ID값_파일명" 형식)
			// -> 랜덤ID 값 추출은 UUID 클래스 활용(범용 고유 식별자)
			String uuid = UUID.randomUUID().toString();
			System.out.println("업로드 될 파일명 : " + uuid + "_" + originalFileName);
			
			// 파일명을 결합하여 보관할 변수에 문자열 결합
			originalFileNames += originalFileName + "/";
			realFileNames += uuid + "_" + originalFileName + "/";
		}
		
		// BoardVO 객체에 원본 파일명과 업로드 될 파일명 저장
		board.setBoard_file(originalFileNames);
		board.setBoard_real_file(realFileNames);
		System.out.println("(최종)원본 파일명 : " + board.getBoard_file());
		System.out.println("(최종)업로드 될 파일명 : " + board.getBoard_real_file());
		// -> 실제로는 UUID를 결합한 파일명만 사용하여 원본파일명과 실제파일명 모두 처리 가능
		// -> 실제파일명에서 가장 먼저 만나는 "_' 기호를 기준으로 분리하면
		//    두번째 배열 인덱스 데이터가 원본 파일명이 된다!
		//----------------------------------------------------------------------------
		// Service 객체의 registBoard() 호출하여 게시물 등록 작업 요청
		int insertCount = service.registBoard(board);
		
		// 등록 성공/실패에 따른 포워딩 작업 수행
		if(insertCount > 0 ) { // 성공
			try {
				// 파일 등록 작업 성공 후 반드시 실제 폴더 위치에 업로드 수행 필요!
				// -> MultipartFile 객체는 임시 경로에 파일을 업로드하므로
				//    작업 성공시 transferTo() 호출하여 실제 위치로 이동 작업 필요
				// MultipartFile 배열 크기만큼 반복
				for(int i = 0; i < board.getFiles().length; i++) {
					// 하나씩 배열에서 객체 꺼내기
					MultipartFile mFile = board.getFiles()[i];
					mFile.transferTo(
						new File(saveDir, board.getBoard_real_file().split("/")[i])
						);
					
				}// for문
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			// 글 목록 페이지 (BoardList.bo)로 리다이렉트
			return "redirect:/BoardList.bo";
		} else { // 실패
			// "msg" 속성명으로 "수정 실패!" 메세지 전달 후 fail_back 포워딩
			model.addAttribute("msg", "글 쓰기 실패!");
			return "fail_back";
		}
		
	}// writePro()
	
		
 }// BoardController
