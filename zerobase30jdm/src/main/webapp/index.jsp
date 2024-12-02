<% response.setHeader("Access-Control-Allow-Origin", "*"); %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        /* 클릭된 행 색상 스타일 */
        .clicked-row {
            background-color: #d1e7dd !important; /* 연한 녹색 */
        }
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
    document.addEventListener('DOMContentLoaded', function () {
        const rows = document.querySelectorAll('table tbody tr');

        rows.forEach(row => {
            row.addEventListener('click', function () {
                // 모든 행의 클릭 효과 초기화
                rows.forEach(r => r.classList.remove('clicked-row'));

                // 클릭된 행에 효과 추가
                this.classList.add('clicked-row');
            });
        });
    });
</script>

<script>
    function getLocation() {
        // 서울 주요 지역 좌표 목록 (모두 실제 좌표값입니다)
        const seoulLocations = [
            { name: '서울금천구', lat: 37.44910833, lnt: 126.9041972},
            { name: '서울구로구', lat: 37.49265, lnt: 126.8895972},
        ];

        // 랜덤하게 한 장소 선택
        const randomLocation = seoulLocations[Math.floor(Math.random() * seoulLocations.length)];

        document.getElementById("lat").value = randomLocation.lat;
        document.getElementById("lnt").value = randomLocation.lnt;

    }

    function getNearbyWifi() {
        var lat = document.getElementById("lat").value.trim();
        var lnt = document.getElementById("lnt").value.trim();
        var radius = 30;

        if (!lat || !lnt || lat === "0.0" || lnt === "0.0") {
            alert("위치 정보를 입력해주세요.");
            return;
        }

        var url = "http://192.168.219.100:8080/wifi-info?action=nearby&lat=" +
            encodeURIComponent(lat) + "&lnt=" + encodeURIComponent(lnt) +
            "&radius=" + radius;

        console.log("요청 URL:", url);

        // 위치 히스토리 저장 후 와이파이 정보 조회
        fetch('http://192.168.219.100:8080/location/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                latitude: lat,
                longitude: lnt
            })
        })
            .then(response => {
                if (!response.ok) throw new Error('위치 히스토리 저장 실패');
                return fetch(url);
            })
            .then(response => {
                if (!response.ok) throw new Error('HTTP error! status: ' + response.status);
                return response.json();
            })
            .then(data => {
                console.log('API 응답 데이터:', data); // 응답 데이터 구조 확인
                if (Array.isArray(data)) {
                    updateWifiList(data);
                } else if (data.error) {
                    alert(data.error);
                } else {
                    throw new Error('잘못된 데이터 형식입니다.');
                }
            })
            .catch(error => {
                console.error('에러:', error);
                alert('데이터를 가져오는데 실패했습니다: ' + error.message);
            });
    }

    function updateWifiList(wifiList) {
        var tbody = document.getElementById("wifi-list");
        tbody.innerHTML = "";

        if (!wifiList || !Array.isArray(wifiList) || wifiList.length === 0) {
            var emptyRow = document.createElement("tr");
            emptyRow.innerHTML = '<td colspan="17" style="text-align: center;">근처 WiFi 정보를 찾을 수 없습니다.</td>';
            tbody.appendChild(emptyRow);
            return;
        }

        wifiList.forEach(function(item) {
            var row = document.createElement("tr");

            var wifiNameCell = `<td>
            <a href="javascript:void(0)"
               onclick="showWifiDetail('${item.mgrNo}')"
               style="color: blue; text-decoration: underline; cursor: pointer;">
                ${item.name || ''}
            </a>
        </td>`;

            var html =
                '<td>' + (Number(item.distance).toFixed(4) || '') + '</td>' +
                '<td>' + (item.mgrNo || '') + '</td>' +
                '<td>' + (item.borough || '') + '</td>' +
                '<td><a href="javascript:void(0)" onclick="showWifiDetail(\'' + item.mgrNo + '\')">' +
                (item.name || '') + '</a></td>' +
                '<td>' + (item.address1 || '') + '</td>' +
                '<td>' + (item.address2 || '') + '</td>' +
                '<td>' + (item.installFloor || '') + '</td>' +
                '<td>' + (item.installType || '') + '</td>' +
                '<td>' + (item.installAgency || '') + '</td>' +        // 설치기관
                '<td>' + (item.serviceType || '') + '</td>' +          // 서비스 구분
                '<td>' + (item.networkType || '') + '</td>' +          // 망종류
                '<td>' + (item.installYear || '') + '</td>' +          // 설치년도
                '<td>' + (item.indoorOutdoor || '') + '</td>' +        // 실내외구분
                '<td>' + (item.remarks || '') + '</td>' +              // WIFI접속환경
                '<td>' + (item.latitude || '') + '</td>' +             // X좌표
                '<td>' + (item.longitude || '') + '</td>' +            // Y좌표
                '<td>' + (item.workDateTime || '') + '</td>';          // 작업일자

            row.innerHTML = html;
            tbody.appendChild(row);
        });
    }

    // 새로 추가된 함수 - 와이파이 상세 정보 페이지로 이동
    function showWifiDetail(mgrNo) {
        window.location.href = 'wifi-detail.jsp?mgrNo=' + encodeURIComponent(mgrNo);
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

    // 북마크 그룹 목록 불러오기
    function loadBookmarkGroups() {
        fetch("http://192.168.219.100:8080/bookmark-group/list")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch bookmark groups");
                }
                return response.json();
            })
            .then(groups => {
                const select = document.getElementById("bookmark-group-select");
                select.innerHTML = '<option value="">즐겨찾기 그룹 이름 선택</option>';
                groups.forEach(group => {
                    const option = document.createElement("option");
                    option.value = group.id;
                    option.textContent = group.name;
                    select.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error loading bookmark groups:", error);
            });
    }
</script>
</body>
</html>