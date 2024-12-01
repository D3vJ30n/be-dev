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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>즐겨찾기 그룹 수정</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f8f9fa;
        }
        .container {
            max-width: 500px;
            margin: 50px auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }
        .button-edit {
            background-color: #28a745; /* 수정 버튼 색상 */
            color: white; /* 텍스트 색상 */
            padding: 8px 16px;
            border-radius: 4px;
            font-size: 16px;
            border: none;
            cursor: pointer;
            text-align: center;
        }

        .button-edit:hover {
            background-color: #218838; /* 호버 효과 */
        }
    </style>
</head>
<body>
<div class="container">
    <h1>즐겨찾기 그룹 수정</h1>
    <%
        int id = Integer.parseInt(request.getParameter("id"));
        BookmarkGroupController controller = new BookmarkGroupController();
        BookmarkGroup group = controller.getBookmarkGroup(id);

        if (group == null) {
    %>
    <p style="text-align: center; color: #dc3545;">해당 ID의 즐겨찾기 그룹을 찾을 수 없습니다.</p>
    <a href="bookmark-group.jsp" class="btn">돌아가기</a>
    <%
    } else {
    %>
    <form action="bookmark-group-edit-submit.jsp" method="post">
        <input type="hidden" name="id" value="<%= group.getId() %>">
        <div class="form-group">
            <label for="name" style="text-align: center; display: block;">그룹 이름</label>
            <input type="text" id="name" name="name" class="form-control" value="<%= group.getName() %>" required>
        </div>
        <div class="form-group">
            <label for="order_no" style="text-align: center; display: block;">순서</label>
            <input type="number" id="order_no" name="order_no" class="form-control" value="<%= group.getOrderNo() %>" required>
        </div>
        <button type="submit" class="button button-edit" style="display: block; margin: 0 auto;">수정</button>
    </form>


    <%
        }
    %>
</div>
</body>
</html>