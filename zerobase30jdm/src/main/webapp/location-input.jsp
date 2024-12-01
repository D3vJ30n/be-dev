<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>위치 정보 입력</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
        }
        form {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            font-weight: bold;
        }
        .form-control {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn-submit {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #04AA6D;
            color: white;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-submit:hover {
            background-color: #028a57;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>위치 정보 입력</h1>
    <form action="location-save.jsp" method="post">
        <div class="form-group">
            <label for="latitude">위도</label>
            <input type="number" id="latitude" name="latitude" class="form-control" step="0.000001" required>
        </div>
        <div class="form-group">
            <label for="longitude">경도</label>
            <input type="number" id="longitude" name="longitude" class="form-control" step="0.000001" required>
        </div>
        <button type="submit" class="btn-submit">저장</button>
    </form>
</div>
</body>
</html>
