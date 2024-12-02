<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="controller.BookmarkController"%>
<%@ page import="model.Bookmark"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>즐겨찾기를 정말 삭제하시겠습니까?</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th {
            background-color: #04AA6D;
            color: white;
            width: 150px;
            text-align: left;
            padding: 12px;
            border: 1px solid #ddd;
        }
        td {
            background-color: #f9f9f9;
            padding: 12px;
            border: 1px solid #ddd;
        }
        .button-group {
            text-align: center;
            margin-top: 20px;
        }
        .button {
            display: inline-block;
            text-decoration: none;
            padding: 8px 16px;
            margin: 0 5px;
            border-radius: 4px;
            cursor: pointer;
            border: none;
        }
        .button-back {
            background-color: #6c757d;
            color: white;
        }
        .button-delete {
            background-color: #dc3545;
            color: white;
        }
    </style>
</head>
<body>
<h1>즐겨찾기를 정말 삭제하시겠습니까?</h1>

<%
    String bookmarkId = request.getParameter("id");
    BookmarkController controller = new BookmarkController();
    Bookmark bookmark = controller.getBookmark(Integer.parseInt(bookmarkId));
%>

<table>
    <tr>
        <th>북마크 이름</th>
        <td><%= bookmark.getGroupName() %></td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td><%= bookmark.getWifiName() %></td>
    </tr>
    <tr>
        <th>등록일자</th>
        <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(bookmark.getRegDttm()) %></td>
    </tr>
</table>

<div class="button-group">
    <a href="bookmark-list.jsp" class="button button-back">돌아가기</a>
    <button onclick="deleteBookmark(<%= bookmarkId %>)" class="button button-delete">삭제</button>
</div>

<script>
    function deleteBookmark(id) {
        fetch('bookmark-delete.jsp', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ id })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('즐겨찾기가 삭제되었습니다.');
                    location.href = 'bookmark-list.jsp';
                } else {
                    alert('삭제 실패: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('삭제 중 오류가 발생했습니다.');
            });
    }
</script>
</body>
</html>