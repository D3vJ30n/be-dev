<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="controller.LocationController"%>
<%@ page import="service.HistoryService" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>위치 저장 결과</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");
    String latitude = request.getParameter("latitude");
    String longitude = request.getParameter("longitude");

    if (latitude != null && longitude != null) {
        try {
            double lat = Double.parseDouble(latitude);
            double lnt = Double.parseDouble(longitude);

            HistoryService historyService = new HistoryService();
            historyService.saveLocation(lat, lnt);

            out.println("<p>위치 정보가 저장되었습니다.</p>");
        } catch (NumberFormatException e) {
            out.println("<p>유효하지 않은 위도/경도 값입니다.</p>");
        } catch (Exception e) {
            out.println("<p>저장 중 오류가 발생했습니다: " + e.getMessage() + "</p>");
        }
    } else {
        out.println("<p>위도와 경도를 입력해주세요.</p>");
    }
%>
<a href="location-input.jsp">돌아가기</a>
</body>
</html>