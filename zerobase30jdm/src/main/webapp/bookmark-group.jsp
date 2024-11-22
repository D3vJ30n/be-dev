<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="controller.BookmarkGroupController"%>
<%@ page import="model.BookmarkGroup"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>북마크 그룹 관리</title>
    <style>
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

        /* 폼 스타일 */
        .bookmark-form {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 4px;
            margin: 20px 0;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .form-control {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }

        .form-control:focus {
            outline: 3px solid #1a73e8;
            outline-offset: -2px;
        }

        .button {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 5px;
            font-size: 14px;
        }

        .button-primary {
            background-color: #007bff;
            color: white;
        }

        .button-edit {
            background-color: #28a745;
            color: white;
        }

        .button-delete {
            background-color: #dc3545;
            color: white;
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

        /* 메시지 스타일 */
        .alert {
            padding: 12px;
            margin: 10px 0;
            border-radius: 4px;
            display: none;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
<a href="#main-content" class="skip-link">메인 콘텐츠로 바로가기</a>

<div class="container">
    <header role="banner">
        <h1>북마크 그룹 관리</h1>
        <nav role="navigation" aria-label="메인 메뉴">
            <div class="nav-menu">
                <a href="index.jsp" class="nav-link">홈</a>
                <a href="history.jsp" class="nav-link">위치 히스토리 목록</a>
                <a href="load-wifi.jsp" class="nav-link">Open API 와이파이 정보 가져오기</a>
                <a href="bookmark-list.jsp" class="nav-link">북마크 보기</a>
                <a href="bookmark-group.jsp" class="nav-link" aria-current="page">북마크 그룹 관리</a>
            </div>
        </nav>
    </header>

    <main id="main-content" role="main">
        <div id="alert" role="alert" aria-live="polite" class="alert"></div>

        <!-- 북마크 그룹 추가 폼 -->
        <form id="bookmarkGroupForm" class="bookmark-form" onsubmit="return submitForm(event)">
            <div class="form-group">
                <label for="name">북마크 그룹 이름</label>
                <input type="text" id="name" name="name" class="form-control"
                       required aria-required="true"
                       aria-describedby="nameHelp">
                <div id="nameHelp" class="sr-only">북마크 그룹의 이름을 입력하세요.</div>
            </div>
            <div class="form-group">
                <label for="order">순서</label>
                <input type="number" id="order" name="order" class="form-control"
                       required aria-required="true" min="1"
                       aria-describedby="orderHelp">
                <div id="orderHelp" class="sr-only">북마크 그룹의 표시 순서를 입력하세요.</div>
            </div>
            <button type="submit" class="button button-primary" aria-label="북마크 그룹 추가하기">
                추가
            </button>
        </form>

        <!-- 북마크 그룹 목록 -->
        <table role="table" aria-label="북마크 그룹 목록">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">북마크 이름</th>
                <th scope="col">순서</th>
                <th scope="col">등록일자</th>
                <th scope="col">수정일자</th>
                <th scope="col">비고</th>
            </tr>
            </thead>
            <tbody>
            <%
                BookmarkGroupController groupController = new BookmarkGroupController();
                List<BookmarkGroup> groupList = groupController.getBookmarkGroupList();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (groupList.isEmpty()) {
            %>
            <tr>
                <td colspan="6" class="no-data">북마크 그룹이 없습니다.</td>
            </tr>
            <%
            } else {
                for (BookmarkGroup group : groupList) {
            %>
            <tr>
                <td><%= group.getId() %></td>
                <td><%= group.getName() %></td>
                <td><%= group.getOrderNo() %></td>
                <td><%= sdf.format(group.getRegDttm()) %></td>
                <td><%= group.getUpdDttm() != null ? sdf.format(group.getUpdDttm()) : "-" %></td>
                <td>
                    <button onclick="editGroup(<%= group.getId() %>)"
                            class="button button-edit"
                            aria-label="<%= group.getName() %> 그룹 수정">
                        수정
                    </button>
                    <button onclick="deleteGroup(<%= group.getId() %>)"
                            class="button button-delete"
                            aria-label="<%= group.getName() %> 그룹 삭제">
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
    // 폼 제출 처리
    function submitForm(event) {
        event.preventDefault();

        const name = document.getElementById('name').value.trim();
        const order = document.getElementById('order').value;

        if (!name) {
            showAlert('그룹 이름을 입력해주세요.', 'error');
            document.getElementById('name').focus();
            return false;
        }

        if (!order || order < 1) {
            showAlert('올바른 순서를 입력해주세요.', 'error');
            document.getElementById('order').focus();
            return false;
        }

        // AJAX 요청
        const xhr = new XMLHttpRequest();
        xhr.open('POST', 'bookmark-group-add.jsp', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    showAlert('북마크 그룹이 추가되었습니다.', 'success');
                    location.reload();
                } else {
                    showAlert('북마크 그룹 추가 중 오류가 발생했습니다.', 'error');
                }
            }
        };

        xhr.send('name=' + encodeURIComponent(name) + '&order=' + order);
        return false;
    }

    // 그룹 수정
    function editGroup(id) {
        announceToScreenReader('수정 페이지로 이동합니다.');
        location.href = 'bookmark-group-edit.jsp?id=' + id;
    }

    // 그룹 삭제
    function deleteGroup(id) {
        if(confirm('이 북마크 그룹을 삭제하시겠습니까?')) {
            announceToScreenReader('북마크 그룹을 삭제합니다.');

            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'bookmark-group-delete.jsp', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        showAlert('북마크 그룹이 삭제되었습니다.', 'success');
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
        const activeElement = document.activeElement;

        if (e.key === 'Enter') {
            if (activeElement.classList.contains('button-edit')) {
                e.preventDefault();
                const id = activeElement.getAttribute('onclick').match(/\d+/)[0];
                editGroup(id);
            } else if (activeElement.classList.contains('button-delete')) {
                e.preventDefault();
                const id = activeElement.getAttribute('onclick').match(/\d+/)[0];
                deleteGroup(id);
            }
        }
    });

    // 페이지 로드 시 초기화
    window.onload = function() {
        const totalGroups = document.querySelectorAll('table tbody tr').length;
        const message = totalGroups === 0 ?
            '등록된 북마크 그룹이 없습니다.' :
            `총 ${totalGroups}개의 북마크 그룹이 등록되어 있습니다.`;
        announceToScreenReader(message);
    };
</script>
</body>
</html>