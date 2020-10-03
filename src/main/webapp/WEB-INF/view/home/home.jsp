<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String cp=request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
body{
	margin: 0px 0px;
	padding: 0px 0px;
}
#login_box{
	margin: 0px auto;
}
</style>
<script type="text/javascript">
 function login(){
	 var point = document.loginForm;
	 
	 var str = point.userId.value;
	 if(!str){
		 alert("아이디를 입력하세요.");
		 point.userId.focus();
		 return;
	 }
	 
	 str = point.userPwd.value;
	 if(!str){
		 alert("비밀번호를 입력하세요.");
		 point.userPwd.focus();
		 return;
	 }
	 
	 point.action = "<%=cp%>/member/login";
	 point.submit();
 }
</script>

</head>
<body>
	<form method="post" name="loginForm">
		<div id="login_box" style="width: 600px; height: 150px; margin:50px auto; border: 1px solid black; text-align: center;">
			<p>아이디 : <input type="text" id="userId" name="userId"></p>
			<p>비밀번호 : <input type="password" id="userPwd" name="userPwd"></p>
			<button name="login_submit" type="button" onclick="login();">로그인</button>
		</div>
	</form>
</body>
</html>