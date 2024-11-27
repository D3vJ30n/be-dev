<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>와이파이 정보 가져오기</title>
    <style>
        /* 접근성을 위한 스킵 네비게이션 */
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

        /* 고대비 모드 지원 */
        @media (prefers-contrast: more) {
            body {
                background: white;
                color: black;
            }
            .nav-link { color: #0000EE !important; }
            button { border: 2px solid black !important; }
        }

        /* 모션 감소 모드 지원 */
        @media (prefers-reduced-motion: reduce) {
            * {
                animation: none !important;
                transition: none !important;
            }
        }

        .center {
            text-align: center;
            margin-top: 50px;
        }

        .loading {
            display: none;
            color: #666;
            margin: 20px 0;
            padding: 10px;
        }

        .result {
            display: none;
            font-weight: bold;
            color: #008000;
            margin: 20px 0;
            padding: 10px;
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
            padding: 5px;
        }

        .nav-link:hover, .nav-link:focus {
            text-decoration: underline;
            outline: 3px solid #1a73e8;
        }

        button {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
        }

        button:hover {
            background-color: #0056b3;
        }

        button:focus {
            outline: 3px solid #1a73e8;
            outline-offset: 2px;
        }

        button:disabled {
            background-color: #cccccc;
            cursor: not-allowed;
        }

        /* 스크린리더용 텍스트 */
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
<!-- 스킵 네비게이션 -->
<a href="#main-content" class="skip-link">메인 콘텐츠로 바로가기</a>

<div class="container" role="main" id="main-content">
    <h1>와이파이 정보 구하기</h1>

    <!-- 네비게이션 -->
    <nav role="navigation" aria-label="메인 메뉴">
        <div class="nav-menu">
            <a href="index.jsp" class="nav-link">홈</a> |
            <a href="history.jsp" class="nav-link">위치 히스토리 목록</a> |
            <a href="load-wifi.jsp" class="nav-link" aria-current="page">Open API 와이파이 정보 가져오기</a> |
            <a href="bookmark-list.jsp" class="nav-link">북마크 보기</a> |
            <a href="bookmark-group.jsp" class="nav-link">북마크 그룹 관리</a>
        </div>
    </nav>

    <div class="center">
        <!-- 로딩 상태 -->
        <div id="loading" class="loading" role="status" aria-live="polite">
            <p>와이파이 정보를 가져오는 중입니다...</p>
            <div class="sr-only">데이터를 불러오는 중입니다. 잠시만 기다려주세요.</div>
        </div>

        <!-- 데이터 로드 버튼 -->
        <button onclick="loadWifiData()"
                id="loadButton"
                aria-label="와이파이 정보 가져오기"
                aria-describedby="button-description">
            와이파이 정보 가져오기
        </button>
        <div id="button-description" class="sr-only">
            이 버튼을 클릭하면 공공 API에서 와이파이 정보를 가져옵니다.
        </div>

        <!-- 결과 메시지 -->
        <div id="result" class="result" role="status" aria-live="assertive"></div>
    </div>
</div>

<script>
    function loadWifiData() {
        const button = document.getElementById('loadButton');
        const loading = document.getElementById('loading');
        const result = document.getElementById('result');

        // 버튼 비활성화 및 상태 변경
        button.disabled = true;
        button.setAttribute('aria-busy', 'true');

        // 로딩 상태 표시
        loading.style.display = 'block';
        result.style.display = 'none';

        // API 호출
        fetch('http://localhost:8080/wifi-info?action=load')
            .then(response => {
                if (!response.ok) {
                    throw new Error('API 호출 실패');
                }
                return response.json();
            })
            .then(data => {
                loading.style.display = 'none';
                result.style.display = 'block';
                const resultMessage = `${data.totalCount}개의 WIFI 정보를 정상적으로 저장하였습니다.`;
                result.innerHTML = resultMessage;

                // 스크린리더 사용자를 위한 실시간 알림
                announceToScreenReader(resultMessage);

                // 3초 후 홈으로 이동
                setTimeout(() => {
                    announceToScreenReader("홈 페이지로 이동합니다.");
                    window.location.href = 'index.jsp';
                }, 3000);
            })
            .catch(error => {
                loading.style.display = 'none';
                result.style.display = 'block';
                const errorMessage = '와이파이 정보 가져오기에 실패했습니다.';
                result.innerHTML = errorMessage;
                result.style.color = 'red';

                // 에러 메시지 스크린리더 알림
                announceToScreenReader(errorMessage);
                console.error('Error:', error);
            })
            .finally(() => {
                button.disabled = false;
                button.setAttribute('aria-busy', 'false');
            });
    }

    // 스크린리더 알림 함수
    function announceToScreenReader(message) {
        const announcement = document.createElement('div');
        announcement.setAttribute('role', 'status');
        announcement.setAttribute('aria-live', 'polite');
        announcement.classList.add('sr-only');
        announcement.textContent = message;
        document.body.appendChild(announcement);
        setTimeout(() => announcement.remove(), 1000);
    }

    // 키보드 접근성
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' && document.activeElement.tagName === 'BUTTON') {
            e.preventDefault();
            loadWifiData();
        }
    });
</script>
</body>
</html>
