<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8"); // 가장 첫 부분에 삽입
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
        out.println("<p>즐겨찾기 그룹이 성공적으로 수정되었습니다.</p>");
    } catch (Exception e) {
        out.println("<p>오류 발생: " + e.getMessage() + "</p>");
    }
%>
<a href="bookmark-group.jsp">돌아가기</a>
</body>
</html>
