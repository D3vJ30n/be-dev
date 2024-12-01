<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="java.sql.*" %>
<%@ page import="util.DBUtil" %>
<%
    // 북마크 ID를 파라미터에서 가져오기
    int bookmarkId = 0;
    try {
        bookmarkId = Integer.parseInt(request.getParameter("id"));
    } catch (NumberFormatException e) {
        out.println("<script>alert('유효하지 않은 북마크 ID입니다.'); history.back();</script>");
        return;
    }

    // 데이터베이스 연결 및 삭제 로직
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
        // MySQL 드라이버 로드 및 DB 연결
        conn = DBUtil.getConnection();

        // SQL 쿼리 실행
        String sql = "DELETE FROM bookmark_group WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookmarkId);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            // 삭제 성공
            out.println("<script>alert('즐겨찾기가 성공적으로 삭제되었습니다.'); location.href='bookmark-group.jsp';</script>");
        } else {
            // 삭제 실패 (ID가 존재하지 않을 경우)
            out.println("<script>alert('삭제할 즐겨찾기를 찾을 수 없습니다.'); history.back();</script>");
        }
    } catch (Exception e) {
        // 예외 처리
        e.printStackTrace();
        out.println("<script>alert('즐겨찾기 삭제 중 오류 발생: " + e.getMessage() + "'); history.back();</script>");
    } finally {
        // 자원 해제
        if (pstmt != null) try { pstmt.close(); } catch (SQLException ignored) {}
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
    }
%>
