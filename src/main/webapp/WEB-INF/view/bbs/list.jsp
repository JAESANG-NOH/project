<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>
<style type="text/css">
*{
    margin:0; padding: 0;
}
body {
    font-size:14px;
	font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
}
a{
    color:#000000;
    text-decoration:none;
    cursor:pointer;
}
a:active, a:hover {
    text-decoration: underline;
    color:tomato;
}
.title {
    font-weight:bold;
    font-size:16px;
    font-family:나눔고딕, "맑은 고딕", 돋움, sans-serif;
}
.btn {
    color:#333333;
    font-weight:500;
    font-family:"맑은 고딕", 나눔고딕, 돋움, sans-serif;
    border:1px solid #cccccc;
    background-color:#ffffff;
    text-align:center;
    cursor:pointer;
    padding:3px 10px 5px;
    border-radius:4px;
}
.btn:active, .btn:focus, .btn:hover {
    background-color:#e6e6e6;
    border-color:#adadad;
    color:#333333;
}
.boxTF {
    border:1px solid #999999;
    padding:3px 5px 5px;
    border-radius:4px;
    background-color:#ffffff;
    font-family:"맑은 고딕", 나눔고딕, 돋움, sans-serif;
}
.selectField {
    border:1px solid #999999;
    padding:2px 5px 4px;
    border-radius:4px;
    font-family:"맑은 고딕", 나눔고딕, 돋움, sans-serif;
}
</style>

<style type="text/css">
	#paginate {
		clear:both;
		font:12px \"맑은 고딕\",NanumGothic,돋움,Dotum,AppleGothic;
		padding:15px 0px 0px 0px;
		text-align:center;
		height:28px;
		white-space:nowrap;
		}
	#paginate a {
		border:1px solid #ccc;
		height:28px;
		color:#000000;
		text-decoration:none;
		padding:4px 7px 4px 7px;
		margin-left:3px;
		line-height:normal;
		vertical-align:middle;
		outline:none;
		select-dummy: expression(this.hideFocus=true);
	}
	#paginate .curBox {
		border:1px solid #e28d8d; 
		background: #fff; 
		color:#cb3536; 
		font-weight:bold;
		height:28px;
		padding:4px 7px 4px 7px;
		margin-left:3px;
		line-height:normal;
		vertical-align:middle;
	}
	#paginate .numBox {
		border:1px solid #ccc;
		height:28px;
		font-weight:bold;
		text-decoration:none;
		padding:4px 7px 4px 7px;
		margin-left:3px;
		line-height:normal;
		vertical-align:middle;
	}
</style>

<script type="text/javascript">
function searchList() {
	var f=document.searchForm;
	f.submit();
}
</script>
</head>

<body>

<div style="width: 700px; margin: 30px auto 0px;">
<table style="width: 100%; border-spacing: 0px;">
<tr height="45">
	<td align="left" class="title">
		<h3><span>|</span> 게시판</h3>
	</td>
</tr>
</table>

<table style="width: 100%; margin-top: 20px; border-spacing: 0px;">
   <tr height="35">
      <td align="left" width="50%">
          ${dataCount}개(${page}/${total_page} 페이지)
      </td>
      <td align="right">
          &nbsp;
      </td>
   </tr>
</table>

<table style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">
  <tr align="center" bgcolor="#eeeeee" height="35" style="border-top: 2px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
      <th width="60" style="color: #787878;">번호</th>
      <th style="color: #787878;">제목</th>
      <th width="100" style="color: #787878;">작성자</th>
      <th width="80" style="color: #787878;">작성일</th>
      <th width="60" style="color: #787878;">조회수</th>
  </tr>
 
 <c:forEach var="dto" items="${list}">
  <tr align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #cccccc;"> 
      <td>${dto.listNum}</td>
      <td align="left" style="padding-left: 10px;">
           <a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
      </td>
      <td>${dto.userId}</td>
      <td>${dto.created}</td>
      <td>${dto.hitCount}</td>
  </tr>
</c:forEach> 
</table>
 
<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
   <tr height="35">
	<td align="center">
        ${dataCount==0?"등록된 게시물이 없습니다.":paging}
	</td>
   </tr>
</table>

<table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
   <tr height="40">
      <td align="left" width="100">
          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/bbs/list';">새로고침</button>
      </td>
      <td align="center">
          <form name="searchForm" action="<%=cp%>/bbs/list" method="post">
              <select name="search" class="selectField">
				  <option value="all"     ${search=="all"?"selected='selected'":""}>모두</option>
				  <option value="subject" ${search=="subject"?"selected='selected'":""}>제목</option>
				  <option value="content" ${search=="content"?"selected='selected'":""}>내용</option>
			</select>
			<input type="text" name="searchKey" value="${searchKey}" class="boxTF">
            <button type="button" class="btn" onclick="searchList()">검색</button>
        </form>
      </td>
      <td align="right" width="100">
          <button type="button" class="btn" onclick="javascript:location.href='<%=cp%>/bbs/insert';">글올리기</button>
      </td>
   </tr>
</table>
</div>

</body>
</html>