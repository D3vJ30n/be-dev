<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="controller.BookmarkController"%>
<%@ page import="model.Bookmark"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>북마크 목록</title>
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
            .nav-link {
                color: #0000EE !important;
            }
            th {
                background-color: black !important;
                color: white !important;
            }
            tr:nth-child(even) {
                background-color: #f0f0f0 !important;
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

        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #04AA6D;
            color: white;
        }

        .delete-button {
            padding: 8px 16px;
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

        .nav-menu {
            margin: 20px 0;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }

        .nav-link {
            margin: 0 10px;
            color: #007bff;
            text-decoration: none;
        }

        .nav-link:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
        }

        .bookmark-link {
            color: #007bff;
            text-decoration: none;
        }

        .bookmark-link:hover {
            text-decoration: underline;
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

<header role="banner">
    <h1>북마크 목록</h1>
    <nav role="navigation" aria-label="메인 메뉴">
        <div class="nav-menu">
            <a href="index.jsp" class="nav-link" aria-label="홈으로 이동">홈</a>
            <a href="history.jsp" class="nav-link">위치 히스토리 목록</a>
            <a href="load-wifi.jsp" class="nav-link">Open API 와이파이 정보 가져오기</a>
            <a href="bookmark-list.jsp" class="nav-link" aria-current="page">북마크 보기</a>
            <a href="bookmark-group.jsp" class="nav-link">북마크 그룹 관리</a>
        </div>
    </nav>
</header>

<main id="main-content" role="main">
    <%
        BookmarkController bookmarkController = new BookmarkController();
        List<Bookmark> bookmarkList = bookmarkController.getBookmarkList();
    %>

    <table role="table" aria-label="북마크 목록 테이블">
        <caption class="sr-only">저장된 북마크 목록</caption>
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">북마크 그룹 이름</th>
            <th scope="col">와이파이명</th>
            <th scope="col">등록일자</th>
            <th scope="col">비고</th>
        </tr>
        </thead>
        <tbody>
        <% if (bookmarkList.isEmpty()) { %>
        <tr>
            <td colspan="5" aria-label="데이터 없음" role="cell" style="text-align: center;">
                북마크 목록이 없습니다.
            </td>
        </tr>
        <% } else {
            for (Bookmark bookmark : bookmarkList) { %>
        <tr>
            <td role="cell"><%= bookmark.getId() %></td>
            <td role="cell">
                <a href="bookmark-group.jsp?id=<%= bookmark.getGroupId() %>"
                   class="bookmark-link"
                   aria-label="<%= bookmark.getGroupName() %> 그룹으로 이동">
                    <%= bookmark.getGroupName() %>
                </a>
            </td>
            <td role="cell">
                <a href="wifi-detail.jsp?id=<%= bookmark.getWifiId() %>"
                   class="bookmark-link"
                   aria-label="<%= bookmark.getWifiName() %> 상세정보 보기">
                    <%= bookmark.getWifiName() %>
                </a>
            </td>
            <td role="cell"><%= bookmark.getRegisterDate() %></td>
            <td role="cell">
                <button onclick="deleteBookmark(<%= bookmark.getId() %>)"
                        class="delete-button"
                        aria-label="<%= bookmark.getWifiName() %> 북마크 삭제">
                    삭제
                </button>
            </td>
        </tr>
        <% }
        } %>
        </tbody>
    </table>
</main>

<script>
    // 키보드 접근성 향상
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Delete' && document.activeElement.classList.contains('delete-button')) {
            e.preventDefault();
            const id = document.activeElement.getAttribute('aria-label').match(/\d+/)[0];
            deleteBookmark(id);
        }
    });

    function deleteBookmark(id) {
        if(confirm('이 북마크를 삭제하시겠습니까?')) {
            announceToScreenReader('북마크를 삭제합니다.');

            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'delete-bookmark', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        announceToScreenReader('삭제가 완료되었습니다.');
                        location.reload();
                    } else {
                        announceToScreenReader('삭제 중 오류가 발생했습니다.');
                    }
                }
            };
            xhr.send('id=' + id);
        }
    }

    function announceToScreenReader(message) {
        let announcement = document.createElement('div');
        announcement.setAttribute('role', 'status');
        announcement.setAttribute('aria-live', 'polite');
        announcement.className = 'sr-only';
        announcement.textContent = message;
        document.body.appendChild(announcement);
        setTimeout(() => announcement.remove(), 1000);
    }

    // 페이지 로드 시 스크린리더에 전체 북마크 수 알림
    window.onload = function() {
        const bookmarkCount = document.querySelectorAll('.bookmark-table tbody tr').length;
        const countMessage = bookmarkCount === 0 ?
            '북마크된 항목이 없습니다.' :
            `총 ${bookmarkCount}개의 북마크가 있습니다.`;
        announceToScreenReader(countMessage);
    };
</script>
</body>
</html>