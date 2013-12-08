<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>一覧</h1>

	<table>
		<tr><th>ID</th><th>クラス記号</th><th>学籍番号</th><th>名前</th><th>変更</th><th>削除</th></tr>
		<c:forEach items="${student}" var="studentList">
			<tr>
			<%= System.out.print("さっす") %>
			<td>${student.id}</td>
			<td>${student.className}</td>
			<td>${student.stNum}</td>
			<td>${student.name}</td>
			<td><a href="update?id=${student.id}">変更</a></td>
			<td><a href="delete?id=${student.id}">削除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>