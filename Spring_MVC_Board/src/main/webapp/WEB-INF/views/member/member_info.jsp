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
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.3.js"></script>
<script type="text/javascript">
	function confirmDelete() {
		let result = confirm("정말로 탈퇴하시겠습니까?");
		if(result) {
			location.href = "MemberDelete.me?id=${member.id}";
		}
	}
</script>
</head>
<body>
	<header>
		<!-- Login, Join 링크 표시 영역(inc/top.jsp 페이지 삽입) -->
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	<article>
		<h1>회원 정보</h1>
		<form action="MemberUpdate.me" method="post" name="joinForm">
			<table border="1">
				<tr>
					<td>이름</td>
					<td><input type="text" name="name" id="name" required="required" size="20" value="${member.name}"></td>
				</tr>
				<tr>
					<td>성별</td>
					<td>
						<input type="radio" name="gender" value="남" <c:if test="${member.gender eq '남'}">checked</c:if>>남&nbsp;&nbsp;
						<input type="radio" name="gender" value="여" <c:if test="${member.gender eq '여'}">checked</c:if>>여
					</td>
				</tr>
				<tr>
					<td>E-Mail</td>
					<td>
						<input type="text" name="email1" id="email1" value="${member.email1 }" required="required" size="10">@
						<input type="text" name="email2" id="email2" value="${member.email2 }" required="required" size="10">
						<select name="selectDomain" id="domain">
							<option value="">직접입력</option>	
							<option value="naver.com">naver.com</option>
							<option value="nate.com">nate.com</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>아이디</td>
					<td>
						<input type="text" name="id" id="id" value="${member.id }" required="required" readonly="readonly" size="20" placeholder="4-16자리 영문자,숫자 조합">
						<span id="checkIdResult"><!-- 자바스크립트에 의해 메세지가 표시될 공간 --></span>
					</td>
				</tr>
				<tr>
					<td>패스워드</td>
					<td>
						<input type="password" name="passwd" id="passwd" required="required" size="20">
					</td>
				</tr>
				<tr>
					<td>변경할 패스워드</td>
					<td>
						<input type="password" name="newPasswd" id="newPasswd" size="20" placeholder="8-20자리 영문자,숫자,특수문자 조합">
						<span id="checkPasswdResult"><!-- 자바스크립트에 의해 메세지가 표시될 공간 --></span>
					</td>
				</tr>
				<!-- 변경할 패스워드 확인란 생략 -->
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="정보수정">
						<input type="button" value="취소" onclick="history.back()">
						<input type="button" value="회원탈퇴" onclick="confirmDelete()">
					</td>
				</tr>
			</table>
		</form>
	</article>
</body>
</html>








