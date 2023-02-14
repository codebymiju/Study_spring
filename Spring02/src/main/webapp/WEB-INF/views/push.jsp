<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<!-- inc/top.jsp 페이지 삽입 -->
		<jsp:include page="inc/top.jsp"></jsp:include>
	</header>
	<article>
		<h1>push.jsp</h1>
		<%-- Dispatch 방식 포워딩 시 함께 전달된 request 객체의 msg 속성값 가져와서 출력 --%>
		<h3>msg 속성값 : <%=request.getAttribute("msg") %></h3> <%-- JSP 표현식 사용 --%>
		<h3>msg 속성값 : ${msg }</h3> <%-- EL 사용 --%>
		<hr>
		<h3>test 속성값 제목 : ${test.subject }</h3>
		<h3>test 속성값 내용 : ${test.content }</h3>
	</article>
</body>
</html>















