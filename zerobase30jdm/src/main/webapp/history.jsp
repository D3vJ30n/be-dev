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
    // 키보드 접근성 향상
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Delete' && document.activeElement.classList.contains('delete-button')) {
            e.preventDefault();
            const button = document.activeElement;
            const id = button.getAttribute('aria-label').match(/\d+/)[0];
            deleteHistory(id);
        }
    });

    function deleteHistory(id) {
        if (confirm('이 히스토리를 삭제하시겠습니까?')) {
            updateStatus('삭제를 시작합니다.');

            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'history-delete.jsp', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        updateStatus('삭제가 완료되었습니다.');
                        location.reload();
                    } else {
                        updateStatus('삭제 중 오류가 발생했습니다.');
                    }
                }
            };

            xhr.send('id=' + id);
        }
    }

    function updateStatus(message) {
        const statusElement = document.getElementById('statusMessage');
        statusElement.textContent = message;

        // 스크린리더 사용자를 위한 새로운 상태 메시지 생성
        const announcement = document.createElement('div');
        announcement.setAttribute('role', 'status');
        announcement.setAttribute('aria-live', 'polite');
        announcement.className = 'sr-only';
        announcement.textContent = message;
        document.body.appendChild(announcement);

        setTimeout(() => {
            announcement.remove();
        }, 1000);
    }

    // 페이지 로드 시 전체 개수 알림
    window.onload = function() {
        const totalRows = document.querySelectorAll('table tbody tr').length;
        const message = totalRows === 0 ?
            '위치 히스토리가 없습니다.' :
            `총 ${totalRows}개의 위치 히스토리가 있습니다.`;
        updateStatus(message);
    };
</script>
</body>
</html>