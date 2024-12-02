<%@ page contentType="application/json; charset=utf-8" %>
<%@ page import="controller.BookmarkController" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
    request.setCharacterEncoding("UTF-8");

    ObjectMapper objectMapper = new ObjectMapper();
    try {
        // JSON 요청 파싱
        Map<String, Object> requestData = objectMapper.readValue(request.getReader(), Map.class);
        int id = (int) requestData.get("id");

        // ID 검증
        if (id <= 0) {
            throw new IllegalArgumentException("유효하지 않은 ID 값입니다.");
        }

        BookmarkController controller = new BookmarkController();
        controller.deleteBookmark(id);

        // 성공 응답
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("message", "즐겨찾기가 삭제되었습니다.");
        out.print(objectMapper.writeValueAsString(successResponse));
    } catch (Exception e) {
        // 실패 응답
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", e.getMessage());
        out.print(objectMapper.writeValueAsString(errorResponse));
    }
%>
