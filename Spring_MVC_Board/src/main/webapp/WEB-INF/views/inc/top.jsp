<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	<%--로그아웃 전 한번 질문하는 함수--%>
	function logout() { 
		let isLogout = confirm("로그아웃 하시겠습니까?");
		
		if(isLogout) {
			location.href = "MemberLogout.me"
		}
	}
</script>  
  <!-- 세션 아이디가 존재하지 않으면 login, join 링크 표시 -->
  <!-- 세션 아이디가 존재하면 아이디, logout 링크 표시 -->
<div id="member_area">
  <a href="./">Home</a>
  <c:choose>
  	<c:when test="${empty sessionScope.sId}">
  		 | <a href="MemberLoginForm.me">Login</a> 
  		 | <a href="MemberJoinForm.me">join</a>
  	</c:when>
  	
	<c:otherwise>
  		| <a href="MemberLoginPro.me?id=${sessionScope.sId}">${sessionScope.sId}님</a> 
  	  	| <a href="javascript:logout()">logout</a>
		<c:if test='${sessionScope.sId eq "admin"}'>
	  		| <a href="MemberList.me">관리자페이지</a>
		</c:if>		
	</c:otherwise>  
  </c:choose>
</div>