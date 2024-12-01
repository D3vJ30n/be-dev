<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="java.sql.*" %>
<%@ page import="util.DBUtil" %>
<%
    int bookmarkId = Integer.parseInt(request.getParameter("id"));

    // 데이터베이스 연결 및 쿼리 실행
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
        conn = DBUtil.getConnection();

        String sql = "DELETE FROM bookmark_group WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookmarkId);
        pstmt.executeUpdate();

        response.sendRedirect("bookmark-group.jsp"); // 삭제 후 목록으로 리다이렉트
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<script>alert('즐겨찾기 삭제 중 오류 발생: " + e.getMessage() + "'); history.back();</script>");
    } finally {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
    }
%>
