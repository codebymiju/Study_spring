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
		<h1>회원 목록</h1>
		<table border="1">
			<tr>
				<th width="150">아이디</th>
				<th width="150">이름</th>
				<th width="250">E-Mail</th>
				<th width="150">가입일</th>
				<th width="150"></th>
			</tr>
			<%-- List<Memberbean> 객체(memberList) 만큼 반복하면서 데이터 출력 --%>
			<c:forEach var="member" items="${memberList }">
				<tr>
					<td>${member.id }</td>
					<td>${member.name }</td>
					<td>${member.email }</td>
					<td>${member.date }</td>
					<td>
						<input type="button" value="상세정보조회" onclick="location.href='MemberInfo.me?id=${member.id}'">
						<input type="button" value="삭제" onclick="location.href='MemberDelete.me?id=${member.id}'">
					</td>
				</tr>
			</c:forEach>
		</table>
	</article>
</body>
</html>












