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
		<h1>redirect.jsp</h1>
		<h3>이름(name 파라미터 값) : ${param.name} </h3>
		<h3>나이(age 파라미터 값) : ${param.age }</h3>
		<hr>
	</article>
</body>
</html>