<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="controller.BookmarkController"%>
<%@ page import="model.BookmarkGroup"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>북마크 그룹 관리</title>
    <style>
        /* 접근성 스타일 */
        @media (prefers-reduced-motion: reduce) {
            * {
                animation: none !important;
                transition: none !important;
            }
        }

        @media (prefers-contrast: more) {
            body { background: white; color: black; }
            .nav-link { color: #0000EE !important; }
            th { background-color: black !important; color: white !important; }
            tr:nth-child(even) { background-color: #f0f0f0 !important; }
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

        /* 폼 스타일 */
        .bookmark-form {
            margin: 20px 0;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-color: #f8f9fa;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .form-group input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }

        .form-group input:focus {
            outline: 3px solid #1a73e8;
            outline-offset: -2px;
        }

        /* 테이블 스타일 */
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

        tr:nth-child(even) {
            background-color: #f2f2f2;
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

        .submit-button {
            background-color: #04AA6D;
            color: white;
        }

        .edit-button {
            background-color: #ffc107;
            color: black;
        }

        .delete-button {
            background-color: #dc3545;
            color: white;
        }

        .button:hover {
            opacity: 0.8;
        }

        .button:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
        }

        /* 네비게이션 스타일 */
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
            padding: 5px;
        }

        .nav-link:hover {
            text-decoration: underline;
        }

        .nav-link:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
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

        /* 에러 메시지 스타일 */
        .error-message {
            color: #dc3545;
            font-size: 14px;
            margin-top: 5px;
        }

        /* 알림 메시지 스타일 */
        .alert {
            padding: 12px;
            margin-bottom: 15px;
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

<!-- 알림 메시지 영역 -->
<div id="alertMessage" class="alert" role="alert" aria-live="polite"></div>

<header role="banner">
    <h1>북마크 그룹 관리</h1>
    <nav role="navigation" aria-label="메인 메뉴">
        <div class="nav-menu">
            <a href="index.jsp" class="nav-link" aria-label="홈으로 이동">홈</a>
            <a href="history.jsp" class="nav-link">위치 히스토리 목록</a>
            <a href="load-wifi.jsp" class="nav-link">Open API 와이파이 정보 가져오기</a>
            <a href="bookmark-list.jsp" class="nav-link">북마크 보기</a>
            <a href="bookmark-group.jsp" class="nav-link" aria-current="page">북마크 그룹 관리</a>
        </div>
    </nav>
</header>

<main id="main-content" role="main">
    <form id="bookmarkForm" class="bookmark-form" role="form"
          aria-label="북마크 그룹 추가 폼" onsubmit="return submitForm(event)">
        <div class="form-group">
            <label for="groupName">북마크 그룹 이름</label>
            <input type="text" id="groupName" name="groupName"
                   required aria-required="true"
                   aria-describedby="groupNameHelp">
            <div id="groupNameError" class="error-message" role="alert"></div>
            <span id="groupNameHelp" class="sr-only">북마크 그룹의 이름을 입력하세요</span>
        </div>
        <div class="form-group">
            <label for="groupOrder">순서</label>
            <input type="number" id="groupOrder" name="groupOrder"
                   required aria-required="true" min="1"
                   aria-describedby="groupOrderHelp">
            <div id="groupOrderError" class="error-message" role="alert"></div>
            <span id="groupOrderHelp" class="sr-only">그룹의 표시 순서를 입력하세요</span>
        </div>
        <button type="submit" class="button submit-button"
                aria-label="북마크 그룹 추가하기">추가</button>
    </form>

    <%
        BookmarkController bookmarkController = new BookmarkController();
        List<BookmarkGroup> groupList = bookmarkController.getBookmarkGroupList();
    %>

    <table role="table" aria-label="북마크 그룹 목록 테이블">
        <caption class="sr-only">북마크 그룹 목록</caption>
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
        <% if (groupList.isEmpty()) { %>
        <tr>
            <td colspan="6" aria-label="데이터 없음" role="cell" style="text-align: center;">
                북마크 그룹이 없습니다.
            </td>
        </tr>
        <% } else {
            for (BookmarkGroup group : groupList) { %>
        <tr>
            <td role="cell"><%= group.getId() %></td>
            <td role="cell"><%= group.getName() %></td>
            <td role="cell"><%= group.getOrder() %></td>
            <td role="cell"><%= group.getCreateDate() %></td>
            <td role="cell"><%= group.getModifyDate() %></td>
            <td role="cell">
                <button onclick="editGroup(<%= group.getId() %>)"
                        class="button edit-button"
                        aria-label="그룹 <%= group.getName() %> 수정">
                    수정
                </button>
                <button onclick="deleteGroup(<%= group.getId() %>)"
                        class="button delete-button"
                        aria-label="그룹 <%= group.getName() %> 삭제">
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
    // 폼 제출 처리
    function submitForm(event) {
        event.preventDefault();

        // 폼 유효성 검사
        const groupName = document.getElementById('groupName').value.trim();
        const groupOrder = document.getElementById('groupOrder').value;
        let isValid = true;

        // 그룹 이름 검사
        if (!groupName) {
            showError('groupNameError', '그룹 이름을 입력해주세요.');
            document.getElementById('groupName').focus();
            isValid = false;
        } else {
            clearError('groupNameError');
        }

        // 순서 검사
        if (!groupOrder || groupOrder < 1) {
            showError('groupOrderError', '올바른 순서 번호를 입력해주세요.');
            document.getElementById('groupOrder').focus();
            isValid = false;
        } else {
            clearError('groupOrderError');
        }

        if (isValid) {
            // AJAX를 통한 폼 제출
            const formData = new FormData();
            formData.append('groupName', groupName);
            formData.append('groupOrder', groupOrder);

            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'add-bookmark-group', true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        showAlert('북마크 그룹이 성공적으로 추가되었습니다.', 'success');
                        resetForm();
                        location.reload();
                    } else {
                        showAlert('북마크 그룹 추가 중 오류가 발생했습니다.', 'error');
                    }
                }
            };
            xhr.send(formData);
        }

        return false;
    }

    // 그룹 수정
    function editGroup(id) {
        announceToScreenReader('수정 모드로 전환합니다.');
        location.href = 'bookmark-group-edit.jsp?id=' + id;
    }

    // 그룹 삭제
    function deleteGroup(id) {
        if(confirm('이 북마크 그룹을 삭제하시겠습니까?')) {
            announceToScreenReader('북마크 그룹을 삭제합니다.');

            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'delete-bookmark-group', true);
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

    // 유틸리티 함수들
    function showError(elementId, message) {
        const errorElement = document.getElementById(elementId);
        errorElement.textContent = message;
        errorElement.style.display = 'block';
        announceToScreenReader(message);
    }

    function clearError(elementId) {
        const errorElement = document.getElementById(elementId);
        errorElement.textContent = '';
        errorElement.style.display = 'none';
    }

    function showAlert(message, type) {
        const alertElement = document.getElementById('alertMessage');
        alertElement.textContent = message;
        alertElement.className = 'alert alert-' + type;
        alertElement.style.display = 'block';
        announceToScreenReader(message);
        setTimeout(() => {
            alertElement.style.display = 'none';
        }, 3000);
    }

    function resetForm() {
        document.getElementById('bookmarkForm').reset();
        clearError('groupNameError');
        clearError('groupOrderError');
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

    // 키보드 접근성
    document.addEventListener('keydown', function(e) {
        const activeElement = document.activeElement;

        if (e.key === 'Enter') {
            if (activeElement.classList.contains('edit-button')) {
                e.preventDefault();
                const id = activeElement.getAttribute('aria-label').match(/그룹 (.*) 수정/)[1];
                editGroup(id);
            } else if (activeElement.classList.contains('delete-button')) {
                e.preventDefault();
                const id = activeElement.getAttribute('aria-label').match(/그룹 (.*) 삭제/)[1];
                deleteGroup(id);
            }
        } else if (e.key === 'Delete' && activeElement.classList.contains('delete-button')) {
            e.preventDefault();
            const id = activeElement.getAttribute('aria-label').match(/그룹 (.*) 삭제/)[1];
            deleteGroup(id);
        }
    });

    // 페이지 로드 시 초기화
    window.onload = function() {
        // 폼 필드에 포커스 이벤트 리스너 추가
        document.getElementById('groupName').addEventListener('focus', function() {
            announceToScreenReader('북마크 그룹 이름을 입력하세요.');
        });

        document.getElementById('groupOrder').addEventListener('focus', function() {
            announceToScreenReader('그룹의 표시 순서를 입력하세요.');
        });

        // 그룹 수 알림
        const groupCount = document.querySelectorAll('table tbody tr').length;
        const countMessage = groupCount === 0 ?
            '등록된 북마크 그룹이 없습니다.' :
            `총 ${groupCount}개의 북마크 그룹이 등록되어 있습니다.`;
        announceToScreenReader(countMessage);
    };
</script>
</body>
</html>