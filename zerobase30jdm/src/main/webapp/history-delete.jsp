<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="controller.HistoryController"%>

<%
    // 캐시 방지
    response.setHeader("Cache-Control", "no-store");
    request.setCharacterEncoding("UTF-8");

    try {
        // ID 파라미터 받기
        int id = Integer.parseInt(request.getParameter("id"));

        // ID 유효성 검사
        if (id <= 0) {
            throw new IllegalArgumentException("유효하지 않은 ID입니다.");
        }

        HistoryController controller = new HistoryController();
        controller.deleteHistory(id);

        response.setStatus(HttpServletResponse.SC_OK);

    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("유효하지 않은 ID 형식입니다.");

    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("삭제 중 오류가 발생했습니다.");
        e.printStackTrace();
    }
%>