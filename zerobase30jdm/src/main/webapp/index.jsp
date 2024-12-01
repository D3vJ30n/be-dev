<% response.setHeader("Access-Control-Allow-Origin", "*"); %>
<%@ page contentType="text/html; charset=utf-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
    request.setCharacterEncoding("UTF-8"); // 가장 첫 부분에 삽입
%>
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
            text-align: center;
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
            text-align: center;
        }

        button {
            margin-right: 10px;
            padding: 5px 10px;
        }

        button:focus {
            outline: 2px solid #04AA6D; /* 버튼 포커스 시 테두리 강조 */
        }

        .action-button {
            padding: 8px 16px; /* 버튼 내부 여백 */
            background-color: #007bff; /* 버튼 배경색 */
            color: white; /* 버튼 글자색 */
            border: none; /* 테두리 제거 */
            border-radius: 4px; /* 버튼 모서리를 둥글게 */
            cursor: pointer; /* 마우스 포인터 변경 */
        }

        .action-button:hover {
            background-color: #0056b3; /* 호버 시 버튼 배경색 변경 */
        }

        .action-button:focus {
            outline: 3px solid #1a73e8; /* 포커스 외곽선 */
            outline-offset: 2px; /* 외곽선 간격 */
        }

        /* 네비게이션 메뉴 스타일 */
        .nav-group {
            margin: 20px 0;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 4px;
            text-align: center;
        }

        .nav-group a {
            margin-right: 15px;
        }

        .nav-link {
            margin: 0 10px;
            color: #007bff; /* 기본 글자 색상 */
            text-decoration: none; /* 밑줄 제거 */
            padding: 5px; /* 내부 여백 */
            border-radius: 4px; /* 둥근 모서리 */
            transition: all 0.3s ease; /* 부드러운 호버 효과 */
        }

        .nav-link:hover {
            text-decoration: underline; /* 마우스 호버 시 밑줄 */
        }

        .nav-link:focus {
            outline: 3px solid #1a73e8; /* 포커스 시 외곽선 */
            outline-offset: 2px; /* 외곽선 간격 */
        }

    </style>
</head>
<body>
<h1 style="text-align: center;">와이파이 정보 구하기</h1>

<!-- 네비게이션 메뉴 -->
<div class="nav-group">
    <!-- 네비게이션 링크, aria-label로 목적 설명 -->
    <a href="index.jsp" class="nav-link" aria-label="홈으로 이동">홈</a>
    <a href="history.jsp" class="nav-link" aria-label="위치 히스토리 목록 페이지로 이동">위치 히스토리 목록</a>
    <a href="load-wifi.jsp" class="nav-link" aria-label="Open API 와이파이 정보 가져오기 페이지로 이동">Open API 와이파이 정보 가져오기</a>
    <a href="bookmark-list.jsp" class="nav-link" aria-label="즐겨찾기 보기 페이지로 이동">즐겨찾기 보기</a>
    <a href="bookmark-group.jsp" class="nav-link" aria-label="즐겨찾기 그룹 관리 페이지로 이동">즐겨찾기 그룹 관리</a>
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
    <button onclick="getLocation()" aria-label="현재 위치 정보를 가져옵니다" class="action-button" style="margin-left: 10px;">내 위치 가져오기</button>
    <!-- 버튼: 근처 와이파이 정보 보기 -->
    <button onclick="getNearbyWifi()" aria-label="입력된 위치를 기준으로 근처 와이파이 정보를 가져옵니다" class="action-button">근처 WIFI 정보 보기</button>
</div>

<!-- 와이파이 정보 테이블 -->
<table>
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



    // getNearbyWifi 함수
    function getNearbyWifi() {
        let lat = document.getElementById("lat").value.trim();
        let lnt = document.getElementById("lnt").value.trim();
        let radius = 30; // 반경 값을 30으로 설정

        console.log("Latitude (위도):", lat);
        console.log("Longitude (경도):", lnt);

        if (!lat || !lnt || lat === "0.0" || lnt === "0.0") {
            alert("위치 정보를 입력해주세요.");
            return;
        }

        const url = `http://192.168.219.100:8080/wifi-info?action=nearby&lat=${lat}&lnt=${lnt}&radius=30`;
        console.log("API 요청 URL:", url);

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('API Response:', data);
                updateWifiList(data);  // 받아온 데이터로 테이블 업데이트
                saveLocationHistory(lat, lnt);  // 위치 히스토리 저장
            })

    function updateWifiList(wifiList) {
        const tbody = document.getElementById("wifi-list");
        tbody.innerHTML = ""; // 기존 테이블 데이터 초기화

        if (!Array.isArray(wifiList) || wifiList.length === 0) {
            // 데이터가 없을 경우 처리
            const row = document.createElement("tr");
            row.innerHTML = `<td colspan="17" style="text-align: center;">근처 WiFi 정보를 찾을 수 없습니다.</td>`;
            tbody.appendChild(row);
            return;
        }

        // 반환된 WiFi 데이터를 테이블에 추가
        wifiList.forEach(wifi => {
            const row = document.createElement("tr");
            row.innerHTML = `
            <td>${wifi.distance || "정보 없음"}</td>
            <td>${wifi.mgrNo || "정보 없음"}</td>
            <td>${wifi.borough || "정보 없음"}</td>
            <td>${wifi.name || "정보 없음"}</td>
            <td>${wifi.address1 || "정보 없음"}</td>
            <td>${wifi.address2 || "정보 없음"}</td>
            <td>${wifi.installFloor || "정보 없음"}</td>
            <td>${wifi.installType || "정보 없음"}</td>
            <td>${wifi.installAgency || "정보 없음"}</td>
            <td>${wifi.serviceType || "정보 없음"}</td>
            <td>${wifi.networkType || "정보 없음"}</td>
            <td>${wifi.installYear || "정보 없음"}</td>
            <td>${wifi.indoorOutdoor || "정보 없음"}</td>
            <td>${wifi.remarks || "정보 없음"}</td>
            <td>${wifi.latitude || "정보 없음"}</td>
            <td>${wifi.longitude || "정보 없음"}</td>
            <td>${wifi.workDateTime || "정보 없음"}</td>
        `;
            tbody.appendChild(row);
        });
    }

        function saveLocationHistory(lat, lnt) {
            fetch("http://192.168.219.100:8080/location/save", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ latitude: lat, longitude: lnt }), // JSON 문자열로 전달
            })
                .then((response) => {
                    if (!response.ok) {
                        // HTTP 응답 상태가 OK가 아닐 경우 처리
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json(); // 응답을 JSON 형식으로 처리
                })
                .then((data) => {
                    console.log("Location history saved successfully:", data);
                })
                .catch((error) => {
                    console.error("Error saving location history:", error);
                    alert("Failed to save location history. Please try again later.");
                });
        }
    }
</script>
</body>
</html>