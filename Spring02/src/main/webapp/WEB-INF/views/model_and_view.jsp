<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Model_and_view</title>
</head>
<body>
	<header>
		<jsp:include page="inc/top.jsp"></jsp:include>
	</header>
	<article>
		<h1>Model_and_view! </h1>
<%-- 		<h3> 이름이 넘어 왔습니까 :  ${map.person.name }</h3> --%>
<%-- 		<h3> 나이가 넘어 왔습니까 :  ${map.person.age }</h3> --%>
		<h3> 제목이 넘어 왔습니까 :  ${map2.test.subject }</h3>
		<h3> 내용이 넘어 왔습니까 :  ${map2.test.content }</h3>
	</article>
</body>
</html>
