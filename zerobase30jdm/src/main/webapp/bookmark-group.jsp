<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8"); // 가장 첫 부분에 삽입
%>
<%@ page import="controller.BookmarkGroupController"%>
<%@ page import="model.BookmarkGroup"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>즐겨찾기 그룹 관리</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #04AA6D;
            color: white;
        }
        .button {
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .button-edit {
            background-color: #28a745;
            color: white;
        }
        .button-delete {
            background-color: #dc3545;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .button-delete:hover {
            background-color: #c82333;
        }
        .button-delete:focus {
            outline: 3px solid #dc3545;
            outline-offset: 2px;
        }
        .bookmark-form {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 4px;
            margin: 20px 0;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-control {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }

        /* 네비게이션 메뉴 스타일 */
        .nav-group {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 15px;
            margin: 20px 0;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }

        .nav-link {
            margin: 0 10px;
            color: #007bff;
            text-decoration: none;
            padding: 5px 10px;
            border-radius: 4px;
            transition: all 0.3s ease;
        }

        .nav-link:hover {
            text-decoration: underline;
        }

        .nav-link:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1 style="text-align: center;">즐겨찾기 목록</h1>
        <nav class="nav-group">
            <a href="index.jsp" class="nav-link">홈</a>
            <a href="history.jsp" class="nav-link">위치 히스토리 목록</a>
            <a href="load-wifi.jsp" class="nav-link">Open API 와이파이 정보 가져오기</a>
            <a href="bookmark-list.jsp" class="nav-link">즐겨찾기 목록</a>
            <a href="bookmark-group.jsp" class="nav-link">즐겨찾기 그룹 관리</a>
        </nav>
    </header>

    <main>
        <!-- 북마크 그룹 추가 -->
        <form id="bookmarkGroupForm" class="bookmark-form" action="bookmark-group-add-submit.jsp" method="POST">
            <div class="form-group">
                <label for="name">즐겨찾기 그룹 이름</label>
                <input type="text" id="name" name="name" class="form-control" required aria-required="true">
            </div>
            <div class="form-group">
                <label for="order">순서</label>
                <input type="number" id="order" name="order" class="form-control" required aria-required="true" min="1">
            </div>
            <button type="submit" class="button button-primary">추가</button>
        </form>

        <!-- 북마크 그룹 목록 -->
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>즐겨찾기 그룹 이름</th>
                <th>순서</th>
                <th>등록일자</th>
                <th>수정일자</th>
                <th>비고</th>
            </tr>
            </thead>
            <tbody>
            <%
                BookmarkGroupController groupController = new BookmarkGroupController();
                List<BookmarkGroup> groupList = groupController.getBookmarkGroupList();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (groupList.isEmpty()) {
            %>
            <tr>
                <td colspan="6">즐겨찾기 그룹이 없습니다.</td>
            </tr>
            <%
            } else {
                for (BookmarkGroup group : groupList) {
            %>
            <tr>
                <td><%= group.getId() %></td>
                <td><%= group.getName() %></td>
                <td><%= group.getOrderNo() %></td>
                <td><%= sdf.format(group.getRegDttm()) %></td>
                <td><%= group.getUpdDttm() != null ? sdf.format(group.getUpdDttm()) : "-" %></td>
                <td>
                    <a href="bookmark-group-edit.jsp?id=<%= group.getId() %>" class="button button-edit">수정</a>
                    <form action="bookmark-group-delete-submit.jsp" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= group.getId() %>">
                        <button type="submit" class="button button-delete">삭제</button>
                    </form>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </main>
</div>
</body>
</html>
