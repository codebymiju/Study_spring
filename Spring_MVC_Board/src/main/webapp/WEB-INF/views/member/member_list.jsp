<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<title>Member_list</title>
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
		border-style: groove;
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
	<!-- 12/12 추가사항 + 주소창에서 접근 불가능하게 sId 가져와서 불일치시 접근 막기  -->
	<header>
		<!-- login, join 표시 영역 -->
		<jsp:include page="/inc/top.jsp"></jsp:include>
	</header>
	<!-- 게시판 리스트 -->
	<section id="listForm">
	<h2>회원 목록</h2>
	<table>
		<tr id="tr_top">
			<td width="50px">이름</td>
			<td width="50px">아이디</td>
			<td width="50px">비밀번호</td>
			<td width="100px">이메일</td>
			<td width="50px">성별</td>
			<td width="50px">가입일</td>
		</tr>
		<c:forEach var="member" items="${memberList}">
			<tr>
				<td>${member.name}</td>
				<td>${member.id }</td>
				<td>${member.passwd }</td>
				<td>${member.email }</td>
				<td>${member.gender }</td>
				<td><fmt:formatDate value="${member.date}" pattern="yy-MM-dd"/></td>
			</tr>
			<!-- 12/12 추가사항 + 수정, 삭제 버튼 추가로 회원정보 수정, 삭제 가능하도록 
			  location.href='MemberUpdate.me?id=${member.id}' -->
		</c:forEach>
	</table>
	</section>
</body>
</html>













