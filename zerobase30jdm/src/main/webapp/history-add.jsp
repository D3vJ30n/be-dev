<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="controller.HistoryController"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 위치 정보 입력</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        label {
            display: block;
            margin: 10px 0 5px;
        }
        input {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
        }
        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
        .error {
            color: red;
            margin-top: 10px;
        }
        .success {
            color: green;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<h1>내 위치 정보 입력</h1>
<form method="POST">
    <label for="lat">위도 (Latitude):</label>
    <input type="text" id="lat" name="lat" placeholder="-90 ~ 90 사이의 숫자 입력" aria-label="위도 입력" required>
    <label for="lnt">경도 (Longitude):</label>
    <input type="text" id="lnt" name="lnt" placeholder="-180 ~ 180 사이의 숫자 입력" aria-label="경도 입력" required>
    <button type="submit">위치 저장</button>
</form>

<div id="message">
    <%
        // 캐시 방지
        response.setHeader("Cache-Control", "no-store");
        request.setCharacterEncoding("UTF-8");

        String message = null;
        String messageType = "error";

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

            // 컨트롤러 호출
            HistoryController controller = new HistoryController();
            controller.addHistory(lat, lnt);

            // 성공 메시지
            message = "위치가 성공적으로 저장되었습니다.";
            messageType = "success";

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            message = "위도와 경도는 숫자여야 합니다.";
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            message = e.getMessage();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            message = "저장 중 오류가 발생했습니다.";
            e.printStackTrace();
        }
    %>

    <%
        if (message != null) {
    %>
    <p class="<%= messageType %>"><%= message %></p>
    <%
        }
    %>
</div>
</body>
</html>
