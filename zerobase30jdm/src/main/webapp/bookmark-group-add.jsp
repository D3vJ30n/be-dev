<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8");
%>
<%@ page import="controller.BookmarkGroupController" %>
<%
    // 기존의 JSP 로직
    String name = request.getParameter("name");

    // 수정된 order_no 처리
    String orderNoParam = request.getParameter("order_no");
    int orderNo = 0;
    if (orderNoParam != null && !orderNoParam.isEmpty()) {
        orderNo = Integer.parseInt(orderNoParam);
    }

    BookmarkGroupController controller = new BookmarkGroupController();
    try {
        controller.addBookmarkGroup(name, orderNo);
        if (orderNo <= 0) {
            throw new IllegalArgumentException("순서는 0보다 커야 합니다.");
        }
        out.println("<script>alert('즐겨찾기 그룹 정보를 추가했습니다.'); location.href='/bookmark-group.jsp';</script>");
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<script>alert('오류가 발생했습니다: " + e.getMessage() + "');</script>");
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>즐겨찾기 그룹 추가</title>
    <style>
        /* 기본 스타일 */
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        /* 네비게이션 링크 스타일 */
        .nav-links {
            margin: 10px 0;
            text-align: center;
        }

        .nav-links a {
            color: #333;
            text-decoration: none;
            margin: 0 10px;
        }

        .nav-links a:hover {
            text-decoration: underline;
        }

        /* 폼 스타일 */
        table {
            width: 100%;
            margin-bottom: 20px;
            border-collapse: collapse;
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

        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .button-group {
            margin-top: 10px;
            text-align: center;
        }

        button {
            background-color: #04AA6D;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin: 0 5px;
        }

        /* 접근성 관련 스타일 */
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
            th {
                background-color: black !important;
                color: white !important;
            }
            button {
                border: 2px solid black !important;
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
        }

        .skip-link:focus {
            top: 0;
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

<!-- 스킵 링크: 화면이 로드되면 접근성 기능이 바로 활성화됩니다 -->
<a href="#main-content" class="skip-link">메인 콘텐츠로 바로가기</a>

<div class="container">
    <header>
        <h1>즐겨찾기 그룹 추가</h1>
        <div class="nav-links">
            <a href="index.jsp">홈</a> |
            <a href="history.jsp">위치 히스토리 목록</a> |
            <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
            <a href="bookmark-list.jsp">즐겨찾기 보기</a> |
            <a href="bookmark-group.jsp">즐겨찾기 그룹 관리</a>
        </div>
    </header>

    <!-- 즐겨찾기 그룹 추가 폼 -->
    <form id="bookmarkGroupForm" onsubmit="return submitForm(event)">
        <table>
            <tr>
                <th>즐겨찾기 그룹 이름</th>
                <td><input type="text" id="name" name="name" required aria-required="true" aria-describedby="nameHelp" /></td>
            </tr>
            <tr>
                <th>순서</th>
                <td><input type="number" id="order_no" name="order_no" required min="1" aria-required="true" aria-describedby="orderHelp" /></td>
            </tr>
        </table>

        <div class="button-group">
            <button type="submit">추가</button>
        </div>
    </form>
</div>

<!-- 화면에서 발생하는 메시지나 알림을 스크린리더로 전송 -->
<div id="alert" role="alert" class="sr-only"></div>

<script>
    // 폼 제출 처리
    function submitForm(event) {
        event.preventDefault();

        const name = document.getElementById('name').value.trim();
        const orderNo = document.getElementById('order_no').value;

        if (!name) {
            showAlert('즐겨찾기 그룹 이름을 입력해주세요.', 'error');
            document.getElementById('name').focus();
            return false;
        }

        if (!orderNo || orderNo < 1) {
            showAlert('올바른 순서를 입력해주세요.', 'error');
            document.getElementById('order_no').focus();
            return false;
        }

        // AJAX 요청
        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'bookmark-group-add.jsp', true); // bookmark-add.jsp로 요청
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    // 정상적으로 처리되면 북마크 그룹 페이지로 이동
                    window.location.href = "bookmark-group.jsp"; // 페이지 이동
                } else {
                    alert('즐겨찾기 그룹 추가 중 오류 발생');
                }
            }
        };

// name과 order 변수 정의 후 AJAX로 전송
        let bookmarkName = document.getElementById('name').value.trim();
        let bookmarkOrder = document.getElementById('order_no').value.trim();

        xhr.send('name=' + encodeURIComponent(bookmarkName) + '&order=' + bookmarkOrder);

    }

    // 알림 표시
    function showAlert(message, type) {
        const alert = document.getElementById('alert');
        alert.textContent = message;
        alert.className = type;
        alert.style.display = 'block';

        setTimeout(() => {
            alert.style.display = 'none';
        }, 3000);
    }

    // 스크린리더 알림
    function announceToScreenReader(message) {
        const announcement = document.createElement('div');
        announcement.setAttribute('role', 'status');
        announcement.setAttribute('aria-live', 'polite');
        announcement.className = 'sr-only';
        announcement.textContent = message;
        document.body.appendChild(announcement);
        setTimeout(() => announcement.remove(), 1000);
    }
</script>

</body>
</html>
