<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"> <!-- UTF-8 인코딩 설정 -->
    <title>와이파이 정보 구하기</title>
    <style>
        /* 테이블 스타일 정의 */
        table {
            font-family: Arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        td, th {
            border: 1px solid #ddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #04AA6D; /* 헤더 배경색 */
            color: white; /* 헤더 글자색 */
        }

        tr:nth-child(even) {
            background-color: #f2f2f2; /* 짝수 행 배경색 */
        }

        /* 버튼 그룹 스타일 */
        .button-group {
            margin: 20px 0;
        }

        button {
            margin-right: 10px;
            padding: 5px 10px;
        }

        button:focus {
            outline: 2px solid #04AA6D; /* 버튼 포커스 시 테두리 강조 */
        }

        /* 네비게이션 메뉴 스타일 */
        .nav-group {
            margin-bottom: 20px;
        }

        .nav-group a {
            margin-right: 15px;
        }
    </style>
</head>
<body>
<h1>와이파이 정보 구하기</h1>

<!-- 네비게이션 메뉴 -->
<div class="nav-group">
    <!-- 네비게이션 링크, aria-label로 목적 설명 -->
    <a href="index.jsp" aria-label="홈으로 이동">홈</a> |
    <a href="history.jsp" aria-label="위치 히스토리 목록 페이지로 이동">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp" aria-label="Open API 와이파이 정보 가져오기 페이지로 이동">Open API 와이파이 정보 가져오기</a> |
    <a href="bookmark-list.jsp" aria-label="북마크 보기 페이지로 이동">북마크 보기</a> |
    <a href="bookmark-group.jsp" aria-label="북마크 그룹 관리 페이지로 이동">북마크 그룹 관리</a>
</div>

<!-- 위치 정보 입력 및 버튼 -->
<div class="button-group">
    <!-- 위도 입력 필드와 레이블 -->
    <label for="lat">위도</label>
    <input type="text" id="lat" placeholder="위도를 입력하세요" value="0.0" aria-describedby="lat-desc">
    <div id="lat-desc" hidden>위도를 입력하는 필드입니다.</div>

    <!-- 경도 입력 필드와 레이블 -->
    <label for="lnt">경도</label>
    <input type="text" id="lnt" placeholder="경도를 입력하세요" value="0.0" aria-describedby="lnt-desc">
    <div id="lnt-desc" hidden>경도를 입력하는 필드입니다.</div>

    <!-- 버튼: 현재 위치 가져오기 -->
    <button onclick="getLocation()" aria-label="현재 위치 정보를 가져옵니다">내 위치 가져오기</button>
    <!-- 버튼: 근처 와이파이 정보 보기 -->
    <button onclick="getNearbyWifi()" aria-label="입력된 위치를 기준으로 근처 와이파이 정보를 가져옵니다">근처 WIFI 정보 보기</button>
</div>

<!-- 와이파이 정보 테이블 -->
<table>
    <caption>사용자가 입력한 위치를 기준으로 검색된 근처 와이파이 정보</caption> <!-- 테이블 설명 -->
    <thead>
    <tr>
        <!-- 테이블 헤더 정의 -->
        <th scope="col">거리(Km)</th>
        <th scope="col">관리번호</th>
        <th scope="col">자치구</th>
        <th scope="col">와이파이명</th>
        <th scope="col">도로명주소</th>
        <th scope="col">상세주소</th>
        <th scope="col">설치위치(층)</th>
        <th scope="col">설치유형</th>
        <th scope="col">설치기관</th>
        <th scope="col">서비스구분</th>
        <th scope="col">망종류</th>
        <th scope="col">설치년도</th>
        <th scope="col">실내외구분</th>
        <th scope="col">WIFI접속환경</th>
        <th scope="col">X좌표</th>
        <th scope="col">Y좌표</th>
        <th scope="col">작업일자</th>
    </tr>
    </thead>
    <!-- 동적 데이터 추가를 위한 tbody -->
    <tbody id="wifi-list" aria-live="assertive" aria-relevant="additions">
    <tr>
        <td colspan="17" style="text-align: center;">
            위치 정보를 입력한 후에 조회해 주세요.
        </td>
    </tr>
    </tbody>
</table>

<script>
    // 현재 위치 정보를 가져오는 함수
    function getLocation() {
        if (navigator.geolocation) { // 브라우저에서 geolocation API 지원 여부 확인
            navigator.geolocation.getCurrentPosition(
                function(position) { // 위치 정보를 성공적으로 가져온 경우
                    document.getElementById("lat").value = position.coords.latitude; // 위도 설정
                    document.getElementById("lnt").value = position.coords.longitude; // 경도 설정
                },
                function(error) { // 위치 정보 가져오기 실패 시 처리
                    let errorMsg = ""; // 오류 메시지 변수
                    switch (error.code) {
                        case error.PERMISSION_DENIED:
                            errorMsg = "위치 정보 사용이 허용되지 않았습니다.";
                            break;
                        case error.POSITION_UNAVAILABLE:
                            errorMsg = "위치 정보를 사용할 수 없습니다.";
                            break;
                        case error.TIMEOUT:
                            errorMsg = "위치 정보를 가져오는데 시간이 초과되었습니다.";
                            break;
                        default:
                            errorMsg = "알 수 없는 오류가 발생했습니다.";
                    }
                    alert(errorMsg); // 사용자에게 오류 메시지 표시
                }
            );
        } else {
            alert("이 브라우저에서는 위치 정보를 제공하지 않습니다."); // 지원되지 않는 브라우저 처리
        }
    }

    // 서버에서 근처 와이파이 정보를 가져오는 함수
    function getNearbyWifi() {
        const lat = document.getElementById("lat").value; // 입력된 위도 값
        const lnt = document.getElementById("lnt").value; // 입력된 경도 값

        // 위도와 경도가 입력되지 않은 경우 사용자 알림
        if (!lat || !lnt || lat === "0.0" || lnt === "0.0") {
            alert("위치 정보를 입력해주세요.");
            return;
        }

        // fetch API를 사용해 서버로부터 와이파이 데이터 요청
        fetch(`/wifi?action=nearby&lat=${lat}&lnt=${lnt}`)
            .then(response => response.json()) // JSON 형태로 응답 처리
            .then(data => {
                updateWifiList(data); // 데이터로 테이블 업데이트
                saveLocationHistory(lat, lnt); // 위치 히스토리를 서버에 저장
            })
            .catch(error => {
                console.error("Error fetching nearby WiFi data:", error); // 오류 로그 출력
                alert("와이파이 정보를 가져오는데 실패했습니다."); // 사용자에게 오류 메시지 표시
            });
    }

    // 와이파이 정보 테이블을 업데이트하는 함수
    function updateWifiList(wifiList) {
        const tbody = document.getElementById("wifi-list");
        tbody.innerHTML = ''; // 기존 테이블 데이터 초기화

        if (wifiList.length === 0) { // 검색 결과가 없는 경우 처리
            const row = document.createElement('tr');
            row.innerHTML = `
                <td colspan="17" style="text-align: center;">
                    검색된 와이파이 정보가 없습니다.
                </td>
            `;
            tbody.appendChild(row);
            return;
        }

        // 검색 결과를 테이블에 추가
        wifiList.forEach(wifi => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${wifi.distance}</td>
                <td>${wifi.mgrNo}</td>
                <td>${wifi.wrdofc}</td>
                <td>${wifi.mainNm}</td>
                <td>${wifi.address1}</td>
                <td>${wifi.address2}</td>
                <td>${wifi.instlFloor}</td>
                <td>${wifi.instlTy}</td>
                <td>${wifi.instlMby}</td>
                <td>${wifi.svcSe}</td>
                <td>${wifi.cmcwr}</td>
                <td>${wifi.cnstcYear}</td>
                <td>${wifi.inoutDoor}</td>
                <td>${wifi.remars3}</td>
                <td>${wifi.lat}</td>
                <td>${wifi.lnt}</td>
                <td>${wifi.workDttm}</td>
            `;
            tbody.appendChild(row);
        });
    }

    // 현재 위치를 서버에 저장하는 함수
    function saveLocationHistory(lat, lnt) {
        // fetch API를 사용해 위치 정보를 서버에 저장
        fetch('/wifi?action=saveHistory', {
            method: 'POST', // POST 요청
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded', // 요청 헤더
            },
            body: `lat=${lat}&lnt=${lnt}` // 요청 본문
        });
    }
</script>
</body>
</html>
