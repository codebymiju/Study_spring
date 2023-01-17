<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
<style type="text/css">
	#replyForm {
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
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	<!-- 12/07 6. 게시판 답글 작성 -->
	<section id="replyForm">
		<h1>게시판 답글 작성</h1>
		<form action="BoardReplyPro.bo" name="boardForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="board_num" value="${param.board_num }">
			<input type="hidden" name="pageNum" value="${param.pageNum }">
			<!-- 6.1 답글작성에 필요한 파라미터 확인 -->
			<input type="hidden" name="board_re_ref" value="${board.board_re_ref }">
			<input type="hidden" name="board_re_lev" value="${board.board_re_lev }">
			<input type="hidden" name="board_re_seq" value="${board.board_re_seq }">
		
			<table>
				<!-- 12/07 6 글쓴이, 제목 수정 -->
				<tr>
					<td class="td_left"><label for="board_name">글쓴이</label></td>
					<td class="td_right"><input type="text" name="board_name" required="required"></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_pass">비밀번호</label></td>
					<td class="td_right"><input type="password" name="board_pass" value="${board.board_pass }" required="required" ></td>
				</tr>
				<tr>
					<td class="td_left"><label for="board_subject">제목</label></td>
					<td class="td_right"><input type="text" name="board_subject" value="Re: ${board.board_subject }" required="required"></td>
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
						<!-- 답글 작성 시 파일업로드는 선택사항! -->
						<input type="file" name="board_file">
					</td>
				</tr>
			</table>
			<section id="commandCell">
				<input type="submit" value="답글등록">&nbsp;&nbsp;
				<input type="reset" value="다시쓰기">&nbsp;&nbsp;
				<input type="button" value="취소" onclick="history.back()">
			</section>
		</form>
	</section>
</body>
</html>








