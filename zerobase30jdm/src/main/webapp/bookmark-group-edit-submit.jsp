<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="controller.BookmarkGroupController" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>즐겨찾기 그룹 수정 완료</title>
</head>
<body>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    String name = request.getParameter("name");
    int orderNo = Integer.parseInt(request.getParameter("order_no"));

    BookmarkGroupController controller = new BookmarkGroupController();
    try {
        controller.updateBookmarkGroup(id, name, orderNo);
%>
<script>
    alert("즐겨찾기 그룹이 성공적으로 수정되었습니다.");
    window.location.href = "bookmark-group.jsp"; // 수정 후 이동할 페이지
</script>
<%
} catch (Exception e) {
%>
<script>
    alert("오류 발생: <%= e.getMessage() %>");
    window.history.back(); // 오류 발생 시 이전 페이지로 이동
</script>
<%
    }
%>
</body>
</html>
