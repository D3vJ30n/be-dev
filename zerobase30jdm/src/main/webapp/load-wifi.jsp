<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"> <!-- UTF-8 인코딩 설정 -->
    <title>와이파이 정보 구하기</title>
    <style>
        /* 기본 레이아웃 및 스타일 정의 */
        .center {
            text-align: center;
            margin-top: 50px;
            font-size: 18px;
        }

        /* 네비게이션 메뉴 스타일 */
        .nav-group {
            margin: 20px 0;
        }

        /* 버튼 포커스 시 스타일 강조 */
        button:focus {
            outline: 2px solid #007BFF;
        }

        /* 로딩 및 결과 메시지 강조 스타일 */
        #loading {
            font-weight: bold;
            color: #FF4500;
        }

        #result {
            font-weight: bold;
            color: #008000;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<h1>와이파이 정보 구하기</h1>

<!-- 네비게이션 링크 -->
<div class="nav-group">
    <a href="index.jsp" aria-label="와이파이 정보 구하기 홈 페이지로 이동">홈</a> |
    <a href="history.jsp" aria-label="위치 히스토리 목록 페이지로 이동">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp" aria-label="Open API 와이파이 정보 가져오기 페이지로 이동">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp" aria-label="북마크 보기 페이지로 이동">북마크 보기</a> |
    <a href="bookmark-group.jsp" aria-label="북마크 그룹 관리 페이지로 이동">북마크 그룹 관리</a>
</div>

<!-- 와이파이 정보 로드 영역 -->
<div class="center">
    <!-- 로딩 중 메시지 -->
    <div id="loading" style="display: none;" aria-live="assertive" aria-atomic="true">
        와이파이 정보를 가져오는 중입니다...
    </div>
    <!-- 와이파이 정보 가져오기 버튼 -->
    <button onclick="loadWifiData()" aria-label="와이파이 정보를 가져오는 작업을 시작합니다">와이파이 정보 가져오기</button>
    <!-- 결과 메시지 -->
    <div id="result" style="display: none;" aria-live="assertive" aria-atomic="true">
        <span id="total-count">0</span>개의 와이파이 정보를 정상적으로 저장하였습니다.
    </div>
</div>

<script>
    // 와이파이 데이터를 서버에서 로드하는 함수
    function loadWifiData() {
        const button = document.querySelector('button'); // 버튼 요소
        document.getElementById('loading').style.display = 'block'; // 로딩 메시지 표시
        button.style.display = 'none'; // 버튼 숨김
        button.disabled = true; // 버튼 비활성화

        // 서버로부터 와이파이 정보 요청
        fetch('/wifi-info?action=load')
            .then(response => response.json()) // JSON 응답 처리
            .then(data => {
                document.getElementById('loading').style.display = 'none'; // 로딩 메시지 숨김
                const resultDiv = document.getElementById('result');
                resultDiv.style.display = 'block'; // 결과 메시지 표시

                // 저장된 와이파이 개수 업데이트
                if (data.totalCount > 0) {
                    document.getElementById('total-count').textContent = data.totalCount;
                } else {
                    resultDiv.textContent = '저장할 와이파이 정보가 없습니다.';
                }

                button.disabled = false; // 버튼 다시 활성화
            })
            .catch(error => {
                alert('와이파이 정보 가져오기에 실패했습니다.'); // 사용자 알림
                console.error('Error:', error); // 콘솔에 오류 출력
                document.getElementById('loading').style.display = 'none'; // 로딩 메시지 숨김
                button.style.display = 'block'; // 버튼 다시 표시
                button.disabled = false; // 버튼 다시 활성화
            });
    }
</script>
</body>
</html>
