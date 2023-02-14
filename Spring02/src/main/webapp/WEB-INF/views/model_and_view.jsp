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
		<h1>model_and_view.jsp</h1>
		<%-- Map 객체(속성명 : "map") 내의 PersonVO 객체(키 : "person") 에 접근하여 데이터 가져오기 --%>
<%-- 		<h3>이름 : ${map.person.name }</h3> --%>
<%-- 		<h3>나이 : ${map.person.age }</h3> --%>
		
		<%-- Map 객체(속성명 : "map") 내의 TestVO 객체(키 : "test") 에 접근하여 데이터 가져오기 --%>
		<h3>제목 : ${map.test.subject }</h3>
		<h3>내용 : ${map.test.content }</h3>
	</article>
</body>
</html>









