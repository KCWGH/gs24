<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=162837f6bcd2201d2b2c36b8cf8c9d57&libraries=services"></script>
<title>편의점 목록</title>

<!-- 카드 스타일링을 위한 CSS -->
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f4f4f4;
    }

    h1 {
        text-align: center;
        margin-top: 20px;
    }

    #conveniList {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
        margin: 20px;
    }

    .conveni {
        background-color: white;
        border: 1px solid #ddd;
        border-radius: 8px;
        width: 300px;
        margin: 10px;
        padding: 20px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        cursor: pointer;
        transition: box-shadow 0.3s;
        position: relative; /* 상대 위치 설정 */
    }

    .conveni:hover {
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .conveni p {
        margin: 10px 0;
        font-size: 14px;
        color: #555;
    }

    .conveni p:first-child {
        font-weight: bold;
    }

    .conveni .address {
        color: #777;
        font-size: 12px;
    }

    /* 사진 스타일 추가 */
    .conveni img {
        width: 100%;
        height: auto;
        border-radius: 8px;
        object-fit: cover;
        z-index: 1; /* 이미지가 기본적으로 지도보다 아래에 있도록 */
    }

    /* 작은 지도 스타일 */
    .small-map {
        position: absolute; /* 지도 위치를 카드 위로 설정 */
        top: 0; /* 카드의 상단에 맞추기 */
        left: 0;
        width: 100%;
        height: 100%; /* 이미지 크기에 맞게 지도 크기 설정 */
        border-radius: 8px;
        z-index: 2; /* 지도가 이미지 위로 올라오게 설정 */
        display: none; /* 기본적으로 숨김 */
    }

    #convenienceId {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
    }
</style>

</head>
<body>
	<c:if test="${not empty message}">
		<script type="text/javascript">
			alert("${message}");
		</script>
	</c:if>
	<%@ include file="../common/header.jsp"%>
	<h1>지점 리스트</h1>
	<div id="conveniList">
    <c:forEach var="conveniVO" items="${conveniList}">
        <div class="conveni" data-address="${conveniVO.address}">
            <sec:authorize access="hasRole('ROLE_MEMBER') or isAnonymous()">
                <c:if test="${conveniVO.isEnabled == 1}">
                    <p hidden="hidden" id="convenienceNum">${conveniVO.convenienceId}</p>
                    <p id="convenienceId">${conveniVO.convenienceId}호점</p>
                    <img src="${pageContext.request.contextPath}/resources/images/convenienceStore/convenienceStore.jpg" alt="ConvenienceStore" />
                    <p class="address">${conveniVO.address}</p>
                    <p hidden="hidden">${conveniVO.isEnabled}</p>

                    <div class="small-map" id="map_${conveniVO.convenienceId}"></div>
                </c:if>
            </sec:authorize>
        </div>
    </c:forEach>
</div>

<script type="text/javascript">
$(document).ready(function () {
	
	$("#conveniList").on("click", ".conveni", function () {
        var conveniId = $(this).find("#convenienceNum").text();
        location.href = '../convenienceFood/list?convenienceId=' + conveniId;
    });
	
    var mapContainer, mapOption, geocoder, marker, infowindow;
    
    $("#conveniList").on("mouseenter", ".conveni", function () {
        var $this = $(this);
        var conveniId = $this.find("#convenienceNum").text();
        var address = $this.data("address");

        // 작은 지도 div 활성화
        var smallMap = $("#map_" + conveniId);
        smallMap.show(); // 지도 보이게

        // 지도 옵션
        mapContainer = smallMap[0]; // 작은 지도 컨테이너
        mapOption = {
            center: new kakao.maps.LatLng(37.5665, 126.9780), // 서울
            level: 3
        };

        var map = new kakao.maps.Map(mapContainer, mapOption);
        var geocoder = new kakao.maps.services.Geocoder();

        if (address) {
            geocoder.addressSearch(address, function (result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                    // 마커 생성
                    marker = new kakao.maps.Marker({
                        position: coords,
                        map: map // 마커가 해당 지도에 추가됨
                    });

                    // 인포윈도우 생성
                    infowindow = new kakao.maps.InfoWindow({
                        content: "<div>" + address + "</div>"
                    });
                    infowindow.open(map, marker);

                    // 지도 중심을 마커 위치로 이동
                    map.setCenter(coords);
                } else {
                    console.error("주소 변환 실패: " + address);
                }
            });
        }
    });

    $("#conveniList").on("mouseleave", ".conveni", function () {
        var conveniId = $(this).find("#convenienceNum").text();
        // 지도 숨기기
        $("#map_" + conveniId).hide();
    });
});
</script>

<%@ include file="../common/footer.jsp"%>
</body>
</html>
