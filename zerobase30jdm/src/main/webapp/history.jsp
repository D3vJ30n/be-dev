<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="controller.HistoryController"%>
<%@ page import="model.History"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>위치 히스토리 목록</title>
    <style>
        /* 접근성을 고려한 스타일 */
        @media (prefers-reduced-motion: reduce) {
            * {
                animation: none !important;
                transition: none !important;
            }
        }

        @media (prefers-contrast: more) {
            body {
                background: white;
                color: black;
            }
            .nav-link {
                color: #0000EE !important;
            }
            th {
                background-color: black !important;
                color: white !important;
            }
        }

        .skip-link {
            position: absolute;
            top: -40px;
            left: 0;
            background: #000;
            color: white;
            padding: 8px;
            z-index: 100;
            transition: top 0.3s;
        }

        .skip-link:focus {
            top: 0;
        }

        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }

        .nav-menu {
            margin: 20px 0;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 4px;
            text-align: center;
        }

        .nav-link {
            margin: 0 10px;
            color: #007bff;
            text-decoration: none;
            padding: 5px;
        }

        .nav-link:hover {
            text-decoration: underline;
        }

        .nav-link:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }

        th {
            background-color: #04AA6D;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }

        .delete-button {
            padding: 6px 12px;
            background-color: #dc3545;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .delete-button:hover {
            background-color: #c82333;
        }

        .delete-button:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
        }

        .no-data {
            text-align: center;
            padding: 20px;
            color: #666;
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

        /* 알림 메시지 스타일 추가 */
        .notification {
            padding: 15px;
            margin: 10px 0;
            border-radius: 4px;
            display: none;
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 1000;
            max-width: 300px;
        }

        .notification.success {
            background-color: #d4edda;
            border-color: #c3e6cb;
            color: #155724;
        }

        .notification.error {
            background-color: #f8d7da;
            border-color: #f5c6cb;
            color: #721c24;
        }

        .input-group {
            margin: 20px 0;
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .input-field {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            flex: 1;
        }

        .save-button {
            padding: 8px 16px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .save-button:hover {
            background-color: #0056b3;
        }

        .save-button:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
        }
    </style>
</head>
<body>
<a href="#main-content" class="skip-link">메인 콘텐츠로 바로가기</a>

<div class="container">
    <header>
        <h1>위치 히스토리 목록</h1>
        <nav role="navigation" aria-label="메인 메뉴">
            <div class="nav-menu">
                <a href="index.jsp" class="nav-link" aria-label="홈으로 이동">홈</a>
                <a href="history.jsp" class="nav-link" aria-current="page">위치 히스토리 목록</a>
                <a href="load-wifi.jsp" class="nav-link">Open API 와이파이 정보 가져오기</a>
                <a href="bookmark-list.jsp" class="nav-link">북마크 보기</a>
                <a href="bookmark-group.jsp" class="nav-link">북마크 그룹 관리</a>
            </div>
        </nav>
    </header>

    <main id="main-content" role="main">
        <!-- 알림 메시지를 위한 컨테이너 -->
        <div id="notification" class="notification" role="alert" aria-live="polite"></div>

        <!-- 위치 추가 폼 -->
        <div class="input-group">
            <label for="latitude">위도:</label>
            <input type="text" id="latitude" class="input-field"
                   placeholder="위도를 입력하세요"
                   aria-label="위도 입력"
                   aria-describedby="lat-format">
            <span id="lat-format" class="sr-only">위도는 -90에서 90 사이의 숫자여야 합니다</span>

            <label for="longitude">경도:</label>
            <input type="text" id="longitude" class="input-field"
                   placeholder="경도를 입력하세요"
                   aria-label="경도 입력"
                   aria-describedby="lng-format">
            <span id="lng-format" class="sr-only">경도는 -180에서 180 사이의 숫자여야 합니다</span>

            <button onclick="addHistory()" class="save-button">저장</button>
        </div>

        <div id="statusMessage" role="status" aria-live="polite" class="sr-only"></div>

        <table role="table" aria-label="위치 히스토리 목록">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">X좌표</th>
                <th scope="col">Y좌표</th>
                <th scope="col">조회일자</th>
                <th scope="col">비고</th>
            </tr>
            </thead>
            <tbody>
            <%
                HistoryController historyController = new HistoryController();
                List<History> historyList = historyController.getHistoryList();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (historyList.isEmpty()) {
            %>
            <tr>
                <td colspan="5" class="no-data">위치 히스토리가 없습니다.</td>
            </tr>
            <%
            } else {
                for (History history : historyList) {
            %>
            <tr>
                <td><%= history.getId() %></td>
                <td><%= String.format("%.6f", history.getLat()) %></td>
                <td><%= String.format("%.6f", history.getLnt()) %></td>
                <td><%= sdf.format(history.getSearchDttm()) %></td>
                <td>
                    <button
                            class="delete-button"
                            onclick="deleteHistory(<%= history.getId() %>)"
                            aria-label="히스토리 ID <%= history.getId() %> 삭제">
                        삭제
                    </button>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </main>
</div>

<script>
    // 알림 메시지 표시 함수
    function showNotification(message, type) {
        const notification = document.getElementById('notification');
        const statusMessage = document.getElementById('statusMessage');

        // 시각적 알림
        notification.textContent = message;
        notification.className = `notification ${type}`;
        notification.style.display = 'block';

        // 스크린 리더를 위한 상태 메시지
        statusMessage.textContent = message;

        // 3초 후 알림 숨기기
        setTimeout(() => {
            notification.style.display = 'none';
            notification.textContent = '';
        }, 3000);
    }

    // 입력값 유효성 검사
    function validateCoordinates(lat, lng) {
        const latNum = parseFloat(lat);
        const lngNum = parseFloat(lng);

        if (isNaN(latNum) || isNaN(lngNum)) {
            return { valid: false, message: "위도와 경도는 숫자여야 합니다." };
        }

        if (latNum < -90 || latNum > 90) {
            return { valid: false, message: "위도는 -90에서 90 사이여야 합니다." };
        }

        if (lngNum < -180 || lngNum > 180) {
            return { valid: false, message: "경도는 -180에서 180 사이여야 합니다." };
        }

        return { valid: true };
    }

    // 위치 정보 추가
    function addHistory() {
        const lat = document.getElementById("latitude").value.trim();
        const lnt = document.getElementById("longitude").value.trim();

        // 필수 입력 확인
        if (!lat || !lnt) {
            showNotification("위도와 경도를 모두 입력해주세요.", "error");
            return;
        }

        // 좌표 유효성 검사
        const validation = validateCoordinates(lat, lnt);
        if (!validation.valid) {
            showNotification(validation.message, "error");
            return;
        }

        fetch('history-add.jsp', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `lat=${encodeURIComponent(lat)}&lnt=${encodeURIComponent(lnt)}`
        })
            .then(response => {
                if (!response.ok) throw new Error('저장에 실패했습니다.');
                return response.text();
            })
            .then(() => {
                showNotification("위치 정보가 저장되었습니다.", "success");
                setTimeout(() => location.reload(), 1000);
            })
            .catch(error => {
                showNotification(error.message, "error");
                console.error("저장 오류:", error);
            });
    }

    // 히스토리 삭제
    function deleteHistory(id) {
        if (confirm(`ID ${id}번 히스토리를 삭제하시겠습니까?`)) {
            fetch('history-delete.jsp', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `id=${encodeURIComponent(id)}`
            })
                .then(response => {
                    if (!response.ok) throw new Error('삭제에 실패했습니다.');
                    return response.text();
                })
                .then(() => {
                    showNotification("히스토리가 삭제되었습니다.", "success");
                    setTimeout(() => location.reload(), 1000);
                })
                .catch(error => {
                    showNotification(error.message, "error");
                    console.error("삭제 오류:", error);
                });
        }
    }

    // 키보드 이벤트 처리
    document.addEventListener('DOMContentLoaded', function() {
        const latInput = document.getElementById('latitude');
        const lngInput = document.getElementById('longitude');

        // Enter 키로 다음 입력 필드로 이동
        latInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                lngInput.focus();
            }
        });

        // 경도 입력 필드에서 Enter 키로 저장
        lngInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                addHistory();
            }
        });
    });
</script>
</body>
</html>