// history-add.jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="controller.HistoryController"%>

<%
    // 캐시 방지
    response.setHeader("Cache-Control", "no-store");
    request.setCharacterEncoding("UTF-8");

    try {
        // 위도, 경도 파라미터 받기
        double lat = Double.parseDouble(request.getParameter("lat"));
        double lnt = Double.parseDouble(request.getParameter("lnt"));

        // 위도, 경도 유효성 검사
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("위도는 -90에서 90 사이여야 합니다.");
        }
        if (lnt < -180 || lnt > 180) {
            throw new IllegalArgumentException("경도는 -180에서 180 사이여야 합니다.");
        }

        HistoryController controller = new HistoryController();
        controller.addHistory(lat, lnt);

        response.setStatus(HttpServletResponse.SC_OK);

    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("위도와 경도는 숫자여야 합니다.");

    } catch (IllegalArgumentException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(e.getMessage());

    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("저장 중 오류가 발생했습니다.");
        e.printStackTrace();
    }
%>