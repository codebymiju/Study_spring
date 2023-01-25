<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%-- EL 에서 표기 방식(날짜 등)을 변경하려면 fmt(format) 라이브러리 필요  --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<!-- 외부 CSS 가져오기 -->
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
<style type="text/css">
	#listForm {
		width: 1024px;
		max-height: 610px;
		margin: auto;
	}
	
	h2 {
		text-align: center;
	}
	
	table {
		margin: auto;
		width: 1024px;
	}
	
	#tr_top {
		background: orange;
		text-align: center;
	}
	
	table td {
		text-align: center;
	}
	
	#subject {
		text-align: left;
		padding-left: 20px;
	}
	
	#pageList {
		margin: auto;
		width: 1024px;
		text-align: center;
	}
	
	#emptyArea {
		margin: auto;
		width: 1024px;
		text-align: center;
	}
	
	#buttonArea {
		margin: auto;
		width: 1024px;
		text-align: right;
		margin-top: 10px;
	}
	
	a {
		text-decoration: none;
	}
</style>
</head>
<body>
	<header>
		<!-- Login, Join 링크 표시 영역 -->
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	<!-- 게시판 리스트 -->
	<section id="listForm">
	<h2>게시판 글 목록</h2>
	<section id="buttonArea">
		<form action="BoardList.bo">
			<!-- 검색 타입 추가 -->
			<select name="searchType">
				<option value="subject" <c:if test="${param.searchType eq 'subject'}">selected</c:if>>제목</option>
				<option value="content" <c:if test="${param.searchType eq 'content'}">selected</c:if>>내용</option>
				<option value="subject_content" <c:if test="${param.searchType eq 'subject_content'}">selected</c:if>>제목&내용</option>
				<option value="name" <c:if test="${param.searchType eq 'name'}">selected</c:if>>작성자</option>
			</select>
			<input type="text" name="keyword" value="${param.keyword }">
			<input type="submit" value="검색">
			&nbsp;&nbsp;
			<input type="button" value="글쓰기" onclick="location.href='BoardWriteForm.bo'" />
		</form>
	</section>
	<table>
		<tr id="tr_top">
			<td width="100px">번호</td>
			<td>제목</td>
			<td width="150px">작성자</td>
			<td width="150px">날짜</td>
			<td width="100px">조회수</td>
		</tr>
		<!-- JSTL 과 EL 활용하여 글목록 표시 작업 반복 -->
		<%-- for(BoardBean board : boardList) {} --%>
		<c:forEach var="board" items="${boardList }">
			<tr>
				<td>${board.board_num }</td>
				<!-- 제목 하이퍼링크(BoardDetail.bo) 연결 -> 파라미터 : 글번호, 페이지번호 -->
				<!-- 만약, pageNum 파라미터가 비어있을 경우 pageNum 변수 선언 및 기본값 1로 설정 -->
				<c:choose>
					<c:when test="${empty param.pageNum }">
						<c:set var="pageNum" value="1" />
					</c:when>
					<c:otherwise>
						<c:set var="pageNum" value="${param.pageNum }" />
					</c:otherwise>
				</c:choose>
				<td id="subject">
					<%-- ======================== 답글 관련 처리 ======================= --%>
					<%-- board_re_lev 값이 0보다 크면 답글이므로 들여쓰기 후 이미지 추가 --%>
					<c:if test="${board.board_re_lev > 0 }">
						<%-- 반복문을 통해 board_re_lev 값 만큼 공백 추가 --%>
						<c:forEach var="i" begin="1" end="${board.board_re_lev }">
							&nbsp;&nbsp;
						</c:forEach>
						<%-- 답글 제목 앞에 이미지 추가 --%>
						<img src="images/re.gif">	
					</c:if>
					<%-- =============================================================== --%>
					<a href="BoardDetail.bo?board_num=${board.board_num }&pageNum=${pageNum }">
						${board.board_subject }
					</a>
				</td>
				<td>${board.board_name }</td>
				<td>
					<%-- JSTL 의 fmt 라이브러리를 활용하여 날짜 표현 형식 변경 --%>
					<%-- fmt:formatDate - Date 타입 날짜 형식 변경 --%>
					<%-- fmt:parseDate - String 타입 날짜 형식 변경 --%>
					<fmt:formatDate value="${board.board_date }" pattern="yy-MM-dd HH:mm"/>
				</td>
				<td>${board.board_readcount }</td>
			</tr>
		</c:forEach>
	</table>
	</section>
	<section id="pageList">
		<!-- 
		현재 페이지 번호(pageNum)가 1보다 클 경우에만 [이전] 링크 동작
		=> 클릭 시 BoardList.bo 서블릿 주소 요청하면서 
		   현재 페이지 번호(pageNum) - 1 값을 page 파라미터로 전달
		-->
		<c:choose>
			<c:when test="${pageNum > 1}">
				<input type="button" value="이전" onclick="location.href='BoardList.bo?pageNum=${pageNum - 1}'">
			</c:when>
			<c:otherwise>
				<input type="button" value="이전">
			</c:otherwise>
		</c:choose>
			
		<!-- 페이지 번호 목록은 시작 페이지(startPage)부터 끝 페이지(endPage) 까지 표시 -->
		<c:forEach var="i" begin="${pageInfo.startPage }" end="${pageInfo.endPage }">
			<!-- 단, 현재 페이지 번호는 링크 없이 표시 -->
			<c:choose>
				<c:when test="${pageNum eq i}">
					${i }
				</c:when>
				<c:otherwise>
					<a href="BoardList.bo?pageNum=${i }">${i }</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<!-- 현재 페이지 번호(pageNum)가 총 페이지 수보다 작을 때만 [다음] 링크 동작 -->
		<c:choose>
			<c:when test="${pageNum < pageInfo.maxPage}">
				<input type="button" value="다음" onclick="location.href='BoardList.bo?pageNum=${pageNum + 1}'">
			</c:when>
			<c:otherwise>
				<input type="button" value="다음">
			</c:otherwise>
		</c:choose>
	</section>
</body>
</html>













