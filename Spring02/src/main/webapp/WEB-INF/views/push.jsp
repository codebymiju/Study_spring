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
		<jsp:include page="inc/top.jsp"></jsp:include>
	</header>
	<article>
		<h1>push push baby</h1>
		<h1>msg 속성값 : ${msg}</h1>
		<h1>msg 속성값 : <%=request.getAttribute("msg") %></h1>
		<hr>
		<h3>test 속성값 제목 : ${test.subject }</h3>
		<h3>test 속성값 제목 : ${test.content }</h3>
	</article>
</body>
</html>