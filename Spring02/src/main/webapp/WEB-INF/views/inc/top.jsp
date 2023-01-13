<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	function logout() {
		let isLogout = confirm("로그아웃 하시겠습니까?");
		
		if(isLogout) {
			location.href = "logout.me";
		}
	}
</script>
<div id="member_area" align="right">
	<a href="./">Home</a> 
	<c:choose>
		<%-- 로그인 상태가 아닐 경우 Login, Join 링크 표시 --%>
		<%-- EL 을 사용하여 세션 객체 접근 시 sessionScope.속성명 으로 접근 --%>
		<c:when test="${empty sessionScope.sId }">
			| <a href="login.me">Login</a> 
			| <a href="MemberJoinForm.me">Join</a>
		</c:when>
		<%-- 로그인 상태일 경우 아이디 표시, Logout 링크 표시 --%>
		<c:otherwise>
			| <a href="MemberInfo.me">${sessionScope.sId }</a>님 
			| <a href="logout.me">Logout</a>
			
			<%-- 만약, 로그인 된 세션 아이디가 "admin" 일 경우 관리자페이지 링크(MemberList.me) 추가 --%>
			<c:if test='${sessionScope.sId eq "admin" }'>
				| <a href="MemberList.me">관리자페이지</a> 
			</c:if>			
		</c:otherwise>
	</c:choose>
</div>

<div id="menu">
	<!--
	하이퍼링크로 루트("/") 경로 지정 시 주소 부분을 제외한 다른 경로가 모두 제거됨
	=> 만약, 서블릿 경로로 주소 뒷부분의 경로(컨텍스트 경로)가 있을 경우 해당 경로 유지를 위해
	   request 객체의 getContextPath() 메서드 리턴값을 경로로 사용하면 루트를 지정하게 된다!
	-->
	<a href="<%=request.getContextPath()%>">홈</a> <!-- home.jsp -->
	<a href="<%=request.getContextPath()%>/main">메인페이지</a> <!-- main.jsp -->
	<a href="<%=request.getContextPath()%>/push">데이터전달</a> <!-- push.jsp -->
	<a href="<%=request.getContextPath()%>/redirect">리다이렉트</a> <!-- redirect.jsp -->
	<a href="<%=request.getContextPath()%>/mav">ModelAndView</a> <!-- model_and_view.jsp -->
	<a href="<%=request.getContextPath()%>/login.me">로그인</a> <!-- member_login_form.jsp -->
	<a href="<%=request.getContextPath()%>/write.bo">글쓰기</a> <!-- qna_board_write.jsp -->
	
</div>


















