<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8"); // 가장 첫 부분에 삽입
%>
<%@ page import="controller.BookmarkGroupController" %>
<%@ page import="model.BookmarkGroup" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>즐겨찾기 그룹 수정</title>
</head>
<body>
<h1>즐겨찾기 그룹 수정</h1>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    BookmarkGroupController controller = new BookmarkGroupController();
    BookmarkGroup group = controller.getBookmarkGroup(id);

    if (group == null) {
%>
<p>해당 ID의 즐겨찾기 그룹을 찾을 수 없습니다.</p>
<%
} else {
%>
<form action="bookmark-group-edit-submit.jsp" method="post">
    <input type="hidden" name="id" value="<%= group.getId() %>">
    <label for="name">그룹 이름:</label>
    <input type="text" id="name" name="name" value="<%= group.getName() %>" required>
    <br>
    <label for="order_no">순서:</label>
    <input type="number" id="order_no" name="order_no" value="<%= group.getOrderNo() %>" required>
    <br>
    <button type="submit">수정</button>
</form>
<%
    }
%>
</body>
</html>
