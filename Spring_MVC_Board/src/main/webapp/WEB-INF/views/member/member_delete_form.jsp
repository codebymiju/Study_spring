<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 외부 CSS 가져오기 -->
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header>
		<!-- Login, Join 링크 표시 영역(inc/top.jsp 페이지 삽입) -->
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	<article>
		<h1>회원 삭제</h1>
		<form action="MemberDeletePro.me" method="post">
			<input type="hidden" name="id" value="${param.id }">
			<table>
				<tr>
					<td>패스워드</td>
					<td><input type="password" name="passwd" required="required" size="20"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="삭제">
						<input type="button" value="돌아가기" onclick="history.back()">
					</td>
				</tr>
			</table>
		</form>
	</article>
</body>
</html>













