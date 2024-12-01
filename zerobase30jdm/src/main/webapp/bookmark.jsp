<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="java.util.List" %>
<%@ page import="model.Bookmark" %>
<%@ page import="model.WifiInfo" %>
<%@ page import="controller.BookmarkController" %>
<%@ page import="dao.WifiInfoDao" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>즐겨찾기 관리</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }
        th { background-color: #f4f4f4; }
        .form-group { margin-bottom: 15px; }
        .form-control { width: 100%; padding: 8px; margin-bottom: 10px; }
        .button { padding: 8px 16px; background-color: #007bff; color: white; border: none; cursor: pointer; }
        .button-delete { background-color: #dc3545; }
    </style>
</head>
<body>
<h1>즐겨찾기 관리</h1>

<!-- 즐겨찾기 추가 폼 -->
<form action="bookmark-group-add.jsp" method="post">
    <div class="form-group">
        <label for="groupId">그룹 선택</label>
        <select id="groupId" name="groupId" class="form-control" required>
            <%-- 즐겨찾기 그룹 목록 표시 --%>
            <%
                BookmarkController bookmarkController = new BookmarkController();
                List<Bookmark> groupList = bookmarkController.getBookmarkList();
                for (Bookmark group : groupList) {
            %>
            <option value="<%= group.getId() %>"><%= group.getGroupName() %></option>
            <%
                }
            %>
        </select>
    </div>

    <div class="form-group">
        <label for="wifiMgrNo">와이파이 선택</label>
        <select id="wifiMgrNo" name="wifiMgrNo" class="form-control" required>
            <%-- 와이파이 목록 표시 --%>
            <%
                WifiInfoDao wifiInfoDao = new WifiInfoDao();
                List<WifiInfo> wifiList = wifiInfoDao.getAllWifiSpots();
                for (WifiInfo wifi : wifiList) {
            %>
            <option value="<%= wifi.getMgrNo() %>"><%= wifi.getName() %></option>
            <%
                }
            %>
        </select>
    </div>

    <button type="submit" class="button">즐겨찾기 추가</button>
</form>

<h2>즐겨찾기 목록</h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>그룹 이름</th>
        <th>와이파이 이름</th>
        <th>등록일시</th>
        <th>삭제</th>
    </tr>
    </thead>
    <tbody>
    <%-- 즐겨찾기 목록 표시 --%>
    <%
        List<Bookmark> bookmarkList = bookmarkController.getBookmarkList();
        if (bookmarkList.isEmpty()) {
    %>
    <tr>
        <td colspan="5">등록된 즐겨찾기가 없습니다.</td>
    </tr>
    <%
    } else {
        for (Bookmark bookmark : bookmarkList) {
    %>
    <tr>
        <td><%= bookmark.getId() %></td>
        <td><%= bookmark.getGroupName() %></td>
        <td><%= bookmark.getWifiName() %></td>
        <td><%= bookmark.getRegDttm() %></td>
        <td>
            <form action="bookmark-group-delete.jsp" method="post" style="display:inline;">
                <input type="hidden" name="id" value="<%= bookmark.getId() %>">
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
</body>
</html>