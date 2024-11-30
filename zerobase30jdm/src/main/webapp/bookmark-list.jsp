<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8"); // 가장 첫 부분에 삽입
%>
<%@ page import="controller.BookmarkController"%>
<%@ page import="model.Bookmark"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>즐겨찾기 목록</title>
    <style>
        /* 접근성 스타일 */
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
            .nav-link { color: #0000EE !important; }
            th {
                background-color: black !important;
                color: white !important;
            }
            .button { border: 2px solid black !important; }
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

        /* 기본 스타일 */
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

        /* 네비게이션 */
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

        /* 테이블 스타일 */
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

        /* 버튼 스타일 */
        .button {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 5px;
        }

        .button-delete {
            background-color: #dc3545;
            color: white;
        }

        .button-delete:hover {
            background-color: #c82333;
        }

        .button:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
        }

        /* 링크 스타일 */
        .detail-link {
            color: #007bff;
            text-decoration: none;
        }

        .detail-link:hover {
            text-decoration: underline;
        }

        .detail-link:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
        }

        /* 알림 메시지 */
        .alert {
            padding: 12px;
            margin: 10px 0;
            border-radius: 4px;
            display: none;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
        }

        .no-data {
            text-align: center;
            padding: 20px;
            color: #666;
        }
    </style>
</head>
<body>
<a href="#main-content" class="skip-link">메인 콘텐츠로 바로가기</a>

<div class="container">
    <header role="banner">
<h1 style="text-align: center;">즐겨찾기 목록</h1>
        <nav role="navigation" aria-label="메인 메뉴">
            <div class="nav-menu">
                <a href="index.jsp" class="nav-link">홈</a>
                <a href="history.jsp" class="nav-link">위치 히스토리 목록</a>
                <a href="load-wifi.jsp" class="nav-link">Open API 와이파이 정보 가져오기</a>
                <a href="bookmark-list.jsp" class="nav-link" aria-current="page">즐겨찾기 보기</a>
                <a href="bookmark-group.jsp" class="nav-link">즐겨찾기 그룹 관리</a>
            </div>
        </nav>
    </header>

    <main id="main-content" role="main">
        <div id="alert" role="alert" aria-live="polite" class="alert"></div>

        <table role="table" aria-label="북마크 목록">
            <thead>
            <tr>
<th scope="col" style="text-align: center;">ID</th>
<th scope="col" style="text-align: center;">즐겨찾기 그룹 이름</th>
<th scope="col" style="text-align: center;">와이파이명</th>
<th scope="col" style="text-align: center;">등록일자</th>
<th scope="col" style="text-align: center;">비고</th>
            </tr>
            </thead>
            <tbody>
            <%
                BookmarkController bookmarkController = new BookmarkController();
                List<Bookmark> bookmarkList = bookmarkController.getBookmarkList();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (bookmarkList.isEmpty()) {
            %>
            <tr>
<td colspan="5" class="no-data" style="color: black;">즐겨찾기가 없습니다.</td>
            </tr>
            <%
            } else {
                for (Bookmark bookmark : bookmarkList) {
            %>
            <tr>
                <td><%= bookmark.getId() %></td>
                <td>
                    <a href="bookmark-group.jsp?id=<%= bookmark.getGroupId() %>"
                       class="detail-link"
                       aria-label="<%= bookmark.getGroupName() %> 그룹으로 이동">
                        <%= bookmark.getGroupName() %>
                    </a>
                </td>
                <td>
                    <a href="wifi-detail.jsp?id=<%= bookmark.getWifiMgrNo() %>"
                       class="detail-link"
                       aria-label="<%= bookmark.getWifiName() %> 상세정보 보기">
                        <%= bookmark.getWifiName() %>
                    </a>
                </td>
                <td><%= sdf.format(bookmark.getRegDttm()) %></td>
                <td>
                    <button onclick="deleteBookmark(<%= bookmark.getId() %>)"
                            class="button button-delete"
                            aria-label="<%= bookmark.getWifiName() %> 즐겨찾기 삭제">
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
    // 즐겨찾기 삭제 처리
    function deleteBookmark(id) {
        if(confirm('이 즐겨찾기를 삭제하시겠습니까?')) {
            announceToScreenReader('즐겨찾기를 삭제합니다.');

            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'bookmark-group-delete.jsp', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        showAlert('즐겨찾기가 삭제되었습니다.', 'success');
                        location.reload();
                    } else {
                        showAlert('삭제 중 오류가 발생했습니다.', 'error');
                    }
                }
            };

            xhr.send('id=' + id);
        }
    }

    // 알림 표시
    function showAlert(message, type) {
        const alert = document.getElementById('alert');
        alert.textContent = message;
        alert.className = 'alert alert-' + type;
        alert.style.display = 'block';
        announceToScreenReader(message);

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

    // 키보드 접근성
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Delete' && document.activeElement.classList.contains('button-delete')) {
            e.preventDefault();
            const button = document.activeElement;
            const id = button.getAttribute('onclick').match(/\d+/)[0];
            deleteBookmark(id);
        }
    });

    // 페이지 로드 시 초기화
    window.onload = function() {
        const totalBookmarks = document.querySelectorAll('table tbody tr').length;
        const message = totalBookmarks === 0 ?
            '즐겨찾기가 없습니다.' :
            `총 ${totalBookmarks}개의 즐겨찾기가 있습니다.`;
        announceToScreenReader(message);
    };
</script>
</body>
</html>