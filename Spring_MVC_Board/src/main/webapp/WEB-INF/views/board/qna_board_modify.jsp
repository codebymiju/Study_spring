<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
<style type="text/css">
	#modifyForm { 
		width: 500px;
		height: 500px;
		border: 1px solid red;
		margin: auto;
	}
	
	h1 {
		text-align: center;
	}
	
	table {
		margin: auto;
		width: 450px;
	}
	
	.td_left {
		width: 150px;
		background: orange;
		text-align: center;
	}
	
	.td_right {
		width: 300px;
		background: skyblue;
	}
	
	#commandCell {
		text-align: center;
	}
</style>
</head>
<body>
	<header>
		<!-- login, join 표시 영역 -->
		<jsp:include page="inc/top.jsp"></jsp:include>
	</header>
	<!-- 게시판 글 수정 -->
	<section id="modifyForm">
		<h1>게시판 글 수정</h1>
		<!-- 글번호,페이지번호 만들고! -->
		<!-- 12/07 01 BoardModifyPro.bo 로 이동 / 글 수정하러! 아래의 hidden 들고 -->
		<!-- 12/07 03 enctype으로 파일수정도 추가 -->
		<form action="BoardModifyPro.bo" name="boardForm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="board_num" value="${param.board_num }">
		<input type="hidden" name="pageNum" value="${param.pageNum }">
		<!-- 12/07 03-(1). 파일 수정 시 기존 파일 삭제를 위해 실제 파일명도 전달 -->
		<input type="hidden" name="board_real_file" value="${board.board_real_file }">
			<table>
				<!-- 12/07 글쓴이, 비밀번호 input type 수정 -->
				<tr>
					<td class="td_left"><label for="board_name">글쓴이</label></td>
					<td class="td_right"><input type="text" name="board_name" value="${board.board_name }" readonly="readonly"></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_pass">비밀번호</label></td>
					<td class="td_right"><input type="password" name="board_pass" value="${board.board_pass }" required="required" ></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_subject">제목</label></td>
					<td class="td_right"><input type="text" name="board_subject" value="${board.board_subject }" required="required"></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_content">내용</label></td>
					<td class="td_right">
						<textarea id="board_content" name="board_content" cols="40" rows="15" required="required">${board.board_content }</textarea>
					</td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_file">파일</label></td>
					<!-- 파일 수정 기능은 제외(파일명만 표시) -->
					<!-- 12/07 03 파일 input타입으로 넣고 기존파일도 표시  -->
					<td class="td_right">
					<input type="file" name="board_file">
					<br>(기존 파일 : ${board.board_file })</td>
				</tr>
			</table>
			<section id="commandCell">
				<input type="submit" value="수정">&nbsp;&nbsp;
				<input type="reset" value="다시쓰기">&nbsp;&nbsp;
				<input type="button" value="취소" onclick="history.back()">
			</section>
		</form>
	</section>
</body>
</html>








