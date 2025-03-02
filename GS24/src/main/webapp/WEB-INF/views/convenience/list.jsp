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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=162837f6bcd2201d2b2c36b8cf8c9d57&libraries=services"></script>
<title>편의점 목록</title>

<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f4f4f4;
    }

    h1 {
        text-align: center;
        margin-top: 100px;
    }

    #conveniList {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
        margin: 30px;
    }

    .conveni {
    background-color: white;
    border: 1px solid #ddd;
    border-radius: 12px;
    width: 100%;
    max-width: 300px;
    margin: 50px auto;
    padding: 25px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.3s;
    position: relative;
	}

    .conveni:hover {
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .conveni p {
        margin: 10px 0;
        font-size: 16px;
        color: #555;
    }

    .conveni p:first-child {
        font-weight: bold;
    }

    .conveni .address {
        color: #777;
        font-size: 16px;
        cursor: pointer;
    }

    .conveni img {
        width: 100%;
        height: auto;
        border-radius: 8px;
        object-fit: cover;
        z-index: 1;
        cursor: pointer;
        object-fit: cover;
    }

    .small-map {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        border-radius: 8px;
        z-index: 2;
        display: none;
    }
    #convenienceId, .address {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
    }
    .setDestination {
    	align-items: center;
    	display: flex;
    	justify-content: center;
    	text-align: center;
    	display: none;
    	cursor: pointer;
	}
	
	ul {
    	display: flex;
    	justify-content: center;
    	padding: 0;
    	margin: 20px 0;
    	list-style-type: none;
	}
	
    .pagination_button {
        display: inline-block;
        margin: 5px;
        text-align: center;
    }

    .pagination_button a {
        text-decoration: none;
        border-radius: 5px;
        color: black;
    }

    .button-container {
        text-align: right;
        margin-bottom: 10px;
    }
    .pagination_button.current a {
    background: #333;
    color: white;
	}
	.pagination_button span {
	color:#444;
    font-size: 30px;
	}
		@media screen and (max-width: 768px) {
		.pagination_button span {
    		font-size: 15px;
		}
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
                        <img class="redirect" src="${pageContext.request.contextPath}/resources/images/convenienceStore/convenienceStore.jpg" alt="ConvenienceStore" />
                        <p class="address">${conveniVO.address}</p>
                        <p hidden="hidden">${conveniVO.isEnabled}</p>
                        <div class="small-map" id="map_${conveniVO.convenienceId}"></div>
                        <p class="setDestination"></p>
                    </c:if>
                </sec:authorize>
            </div>
        </c:forEach>
    </div>
    <form id="listForm" action="list" method="get">
        <input type="hidden" name="pageNum">
        <input type="hidden" name="pageSize">
    </form>

    <!-- 페이징 처리 -->
	<ul>
    	<c:if test="${pageMaker.isPrev()}">
        	<li class="pagination_button">
            	<a href="${pageMaker.startNum - 1}">이전</a>
        	</li>
    	</c:if>

    	<c:forEach begin="${pageMaker.startNum}" end="${pageMaker.endNum}" var="num">
        	<li class="pagination_button">
            	<c:choose>
                	<c:when test="${num == pageMaker.pagination.pageNum}">
                    	<span>●</span>
                	</c:when>
                	<c:otherwise>
                    	<a href="${num}"><span>○</span></a>
                	</c:otherwise>
            	</c:choose>
        	</li>
    	</c:forEach>

    	<c:if test="${pageMaker.isNext()}">
        	<li class="pagination_button">
            	<a href="${pageMaker.endNum + 1}">다음</a>
        	</li>
    	</c:if>
	</ul>

    <script type="text/javascript">
    $(document).ready(function () {
    	
    	$(".pagination_button a").on("click", function(e) {
            var listForm = $("#listForm");
            e.preventDefault();

            var pageNum = $(this).attr("href");
            var pageSize = "<c:out value='${pageMaker.pagination.pageSize }' />";
            var type = "<c:out value='${pageMaker.pagination.type }' />";
            var keyword = "<c:out value='${pageMaker.pagination.keyword }' />";

            listForm.find("input[name='pageNum']").val(pageNum);
            listForm.find("input[name='pageSize']").val(pageSize);
            listForm.find("input[name='type']").val(type);
            listForm.find("input[name='keyword']").val(keyword);
            listForm.submit();
            
            setTimeout(function() {
                $("#conveniList").css('display', 'none').fadeIn(300);
            }, 300);
        });
    	
        $("#conveniList").on("click", ".setDestination", function () {
            var closestConveni = $(this).closest(".conveni");
            var address = closestConveni.data("address");

            var geocoder = new kakao.maps.services.Geocoder();
            geocoder.addressSearch(address, function (result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    var destLat = result[0].y;
                    var destLng = result[0].x;

                    var kakaoMapUrl = "https://map.kakao.com/link/to/" + address + "," + destLat + "," + destLng;
                    window.open(kakaoMapUrl);
                } else {
                    alert("주소 변환에 실패했습니다.");
                }
            });
        });

        $("#conveniList").on("click", ".redirect", function () {
            var closestConveni = $(this).closest(".conveni");
            var conveniId = closestConveni.find("#convenienceNum").text();
            location.href = "../convenienceFood/list?convenienceId=" + conveniId;
        });

        $("#conveniList").on("mouseenter", ".address", function () {
            let address = $(this).closest(".conveni").data("address");
            let originalColor = $(this).css("color");
            let originalFontWeight = $(this).css("font-weight");

            $(this).data("original-color", originalColor);
            $(this).data("original-font-weight", originalFontWeight);

            $(this).text("지도로 보기").css({
                "font-weight": "bold",
                "color": "#4CAF50"
            });
            $(this).siblings(".setDestination").text("도착지로 설정").css({
                "font-weight": "bold",
                "color": "#D84B16"
            }).show();
        });

        $("#conveniList").on("click", ".address", function (event) {
            event.preventDefault();
            var closestConveni = $(this).closest(".conveni");
            var conveniId = closestConveni.find("#convenienceNum").text();
            var address = closestConveni.data("address");

            var smallMap = $("#map_" + conveniId);
            smallMap.show();

            var mapContainer = smallMap[0];
            var mapOption = {
                center: new kakao.maps.LatLng(37.5665, 126.9780),
                level: 3,
                draggable: false,
                scrollwheel: false 
            };

            var map = new kakao.maps.Map(mapContainer, mapOption);
            var geocoder = new kakao.maps.services.Geocoder();

            if (address) {
                geocoder.addressSearch(address, function (result, status) {
                    if (status === kakao.maps.services.Status.OK) {
                        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                        var marker = new kakao.maps.Marker({
                            position: coords,
                            map: map
                        });

                        var infowindow = new kakao.maps.InfoWindow({
                            content: "<div>" + address + "</div>"
                        });

                        infowindow.open(map, marker);
                        map.setCenter(coords);
                    } else {
                        console.error("주소 변환 실패: " + address);
                    }
                });
            }
        });

        $("#conveniList").on("mouseleave", ".address", function () {
            let address = $(this).closest(".conveni").data("address");
            let originalColor = $(this).data("original-color");
            let originalFontWeight = $(this).data("original-font-weight");

            if (address && originalColor && originalFontWeight) {
                $(this).text(address).css({
                    "color": originalColor,
                    "font-weight": originalFontWeight
                });
            }
        });

        $("#conveniList").on("mouseleave", ".conveni", function () {
            var conveniId = $(this).find("#convenienceNum").text();
            $("#map_" + conveniId).hide();
        	$(this).find(".setDestination").hide();
        });
        
    });
    </script>

    <%@ include file="../common/footer.jsp"%>
</body>
</html>