<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 상세정보</title>
    <style>
        /* 기존 테이블 스타일 재사용 */
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
            background-color: #04AA6D;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        /* 상세 정보 페이지를 위한 추가 스타일 */
        .detail-table th {
            width: 20%;
            text-align: left;
        }

        .detail-table td {
            width: 80%;
            text-align: left;
        }

        /* 뒤로가기 버튼 스타일 */
        .back-btn {
            margin: 20px 0;
            padding: 8px 16px;
            background-color: #04AA6D;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .back-btn:hover {
            background-color: #038857;
        }
    </style>
</head>
<body>
<h1>와이파이 상세정보</h1>

<button onclick="history.back()" class="back-btn">뒤로 가기</button>

<table class="detail-table">
    <!-- 북마크 추가 영역 -->
    <div>
        <label for="bookmark-group-select">북마크 그룹</label>
        <select id="bookmark-group-select">
            <option value="">그룹 선택</option>
        </select>
        <button onclick="addBookmark()">즐겨찾기 추가</button>
    </div>

    <tr>
        <th>거리(Km)</th>
        <td id="distance"></td>
    </tr>
    <tr>
        <th>관리번호</th>
        <td id="x_swifi_mgr_no"></td>
    </tr>
    <tr>
        <th>자치구</th>
        <td id="x_swifi_wrdofc"></td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td id="x_swifi_main_nm"></td>
    </tr>
    <tr>
        <th>도로명주소</th>
        <td id="x_swifi_adres1"></td>
    </tr>
    <tr>
        <th>상세주소</th>
        <td id="x_swifi_adres2"></td>
    </tr>
    <tr>
        <th>설치위치(층)</th>
        <td id="x_swifi_instl_floor"></td>
    </tr>
    <tr>
        <th>설치유형</th>
        <td id="x_swifi_instl_ty"></td>
    </tr>
    <tr>
        <th>설치기관</th>
        <td id="x_swifi_instl_mby"></td>
    </tr>
    <tr>
        <th>서비스구분</th>
        <td id="x_swifi_svc_se"></td>
    </tr>
    <tr>
        <th>망종류</th>
        <td id="x_swifi_cmcwr"></td>
    </tr>
    <tr>
        <th>설치년도</th>
        <td id="x_swifi_cnstc_year"></td>
    </tr>
    <tr>
        <th>실내외구분</th>
        <td id="x_swifi_inout_door"></td>
    </tr>
    <tr>
        <th>WIFI접속환경</th>
        <td id="x_swifi_remars3"></td>
    </tr>
    <tr>
        <th>X좌표</th>
        <td id="lat"></td>
    </tr>
    <tr>
        <th>Y좌표</th>
        <td id="lnt"></td>
    </tr>
    <tr>
        <th>작업일자</th>
        <td id="work_dttm"></td>
    </tr>
</table>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const urlParams = new URLSearchParams(window.location.search);
        const mgrNo = urlParams.get('mgrNo');

        if (!mgrNo) {
            alert('관리번호가 없습니다.');
            return;
        }

        fetch('http://192.168.219.100:8080/wifi-info?action=detail&mgrNo=' + encodeURIComponent(mgrNo))
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // WifiSpot 객체의 필드명에 맞춰 데이터 바인딩
                document.getElementById('distance').textContent = data.distance ? Number(data.distance).toFixed(4) : '';
                document.getElementById('x_swifi_mgr_no').textContent = data.mgrNo || '';
                document.getElementById('x_swifi_wrdofc').textContent = data.borough || '';
                document.getElementById('x_swifi_main_nm').textContent = data.name || '';
                document.getElementById('x_swifi_adres1').textContent = data.address1 || '';
                document.getElementById('x_swifi_adres2').textContent = data.address2 || '';
                document.getElementById('x_swifi_instl_floor').textContent = data.installFloor || '';
                document.getElementById('x_swifi_instl_ty').textContent = data.installType || '';
                document.getElementById('x_swifi_instl_mby').textContent = data.installAgency || '';
                document.getElementById('x_swifi_svc_se').textContent = data.serviceType || '';
                document.getElementById('x_swifi_cmcwr').textContent = data.networkType || '';
                document.getElementById('x_swifi_cnstc_year').textContent = data.installYear || '';
                document.getElementById('x_swifi_inout_door').textContent = data.indoorOutdoor || '';
                document.getElementById('x_swifi_remars3').textContent = data.remarks || '';
                document.getElementById('lat').textContent = data.latitude || '';
                document.getElementById('lnt').textContent = data.longitude || '';
                document.getElementById('work_dttm').textContent = data.workDateTime || '';
            })
            .catch(error => {
                console.error('Error:', error);
                alert('데이터를 가져오는데 실패했습니다.');
            });
    });

    loadBookmarkGroups();

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
                select.innerHTML = '<option value="">그룹 선택</option>';
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

    function addBookmark() {
        const selectElement = document.getElementById("bookmark-group-select");
        const selectedGroupId = selectElement.value; // 선택한 그룹 ID
        const selectedWifiMgrNo = new URLSearchParams(window.location.search).get('mgrNo'); // 와이파이 관리 번호

        if (!selectedGroupId) {
            alert("북마크 그룹을 선택하세요.");
            return;
        }

        const wifiDetails = {
            mgrNo: document.getElementById("x_swifi_mgr_no").textContent.trim(),
            name: document.getElementById("x_swifi_main_nm").textContent.trim(),
            address1: document.getElementById("x_swifi_adres1").textContent.trim(),
            address2: document.getElementById("x_swifi_adres2").textContent.trim(),
            latitude: document.getElementById("lat").textContent.trim(),
            longitude: document.getElementById("lnt").textContent.trim(),
        };

        fetch("http://192.168.219.100:8080/bookmark/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                groupId: parseInt(selectedGroupId, 10),
                wifiMgrNo: selectedWifiMgrNo,
            }),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to add bookmark");
                }
                return response.json();
            })
            .then(data => {
                alert("즐겨찾기가 추가되었습니다.");
            })
            .catch(error => {
                console.error("Error adding bookmark:", error);
                alert("Failed to add bookmark: " + error.message);
            });
    }
</script>
</body>
</html>