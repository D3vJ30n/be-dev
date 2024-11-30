<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8"); // 가장 첫 부분에 삽입
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h1 {
            margin-bottom: 20px;
        }

        .nav-links {
            margin: 20px 0;
        }

        .nav-links a {
            text-decoration: none;
            color: #0D47A1;
            margin-right: 15px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th {
            background-color: #04AA6D;
            color: white;
            text-align: center;
            padding: 8px;
            border: 1px solid #ddd;
        }

        td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #result {
            background-color: #d4edda;
            padding: 15px;
            margin: 20px 0;
            border-radius: 4px;
            text-align: center;
            display: none;
        }

        button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            font-size: 16px;
            margin: 20px auto;
            display: block;
        }

        button:hover {
            background-color: #0056b3;
        }

        .sr-only {
            position: absolute;
            width: 1px;
            height: 1px;
            padding: 0;
            margin: -1px;
            overflow: hidden;
            clip: rect(0, 0, 0, 0);
            border: 0;
        }
    </style>
</head>
<body>
<h1>와이파이 정보 구하기</h1>

<div class="nav-links">
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp">즐겨찾기 보기</a> |
    <a href="bookmark-group.jsp">즐겨찾기 그룹 관리</a>
</div>

<button id="loadButton" onclick="loadWifiData()">와이파이 정보 가져오기</button>
<div id="loading">와이파이 정보를 가져오는 중입니다...</div>
<div id="result"></div>

<script>
    function loadWifiData() {
        const button = document.getElementById('loadButton');
        const loading = document.getElementById('loading');
        const result = document.getElementById('result');

        button.disabled = true;
        button.setAttribute('aria-busy', 'true');
        loading.style.display = 'block';
        result.style.display = 'none';

        fetch('/wifi-info?action=load')
            .then(response => {
                if (!response.ok) {
                    throw new Error('API 호출 실패');
                }
                return response.json();
            })
            .then(data => {
                loading.style.display = 'none';
                result.style.display = 'block';
                console.log('Response data:', data); // 응답 데이터 로깅
                result.textContent = `${data.totalCount}개의 WIFI 정보를 정상적으로 저장하였습니다.`;
                announceToScreenReader(`${data.totalCount}개의 와이파이 정보를 저장했습니다.`);
            })
            .catch(error => {
                loading.style.display = 'none';
                result.style.display = 'block';
                result.textContent = '와이파이 정보 가져오기에 실패했습니다.';
                console.error('Error:', error);
                announceToScreenReader('오류가 발생했습니다. 와이파이 정보를 가져오지 못했습니다.');
            })
            .finally(() => {
                button.disabled = false;
                button.setAttribute('aria-busy', 'false');
            });
    }

    function announceToScreenReader(message) {
        const announcement = document.createElement('div');
        announcement.setAttribute('role', 'status');
        announcement.setAttribute('aria-live', 'polite');
        announcement.className = 'sr-only';
        announcement.textContent = message;
        document.body.appendChild(announcement);
        setTimeout(() => announcement.remove(), 1000);
    }

    document.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' && document.activeElement.tagName === 'BUTTON') {
            e.preventDefault();
            loadWifiData();
        }
    });
</script>
</body>
</html>