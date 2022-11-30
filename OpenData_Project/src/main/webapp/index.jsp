<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jQuery 라이브러리 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>

	<h2>실시간 대기 오염 정보</h2>
	
	지역: 
	<select id="location">
		<option>서울</option>
		<option>부산</option>
		<option>대전</option>
		<option>제주</option>
	</select>
	
	<button id="btn1">해당 지역 대기오염정보 보기</button>
	
	<br><br>
	
	<table id="result1" border="1" align="center">
		<thead>
			<tr>
				<th>측정소명</th>
				<th>측정일시</th>
				<th>통합대기환경수치</th>
				<th>미세먼지 농도</th>
				<th>일산화탄소 농도</th>
				<th>이산화질소 농도</th>
				<th>아황산가스 농도</th>
				<th>오존 농도</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	
	<script>
		$(function() {
			
			$("#btn1").click(function() {
				

				/*
				// 응답 데이터를 json 형식으로 돌려받았을 경우
				$.ajax({
					url : "air.do",
					data : {location:$("#location").val()},
					success : function(data) {
						
						// console.log(data);
						// console.log(data.response);
						// console.log(data.response.body);
						// console.log(data.response.body.items);
						
						var itemArr = data.response.body.items;
						
						var value = "";
						for(var i = 0; i < itemArr.length; i++) {
							// console.log(itemArr);
							
							var item = itemArr[i]; //{stationName:"~~", dataTime:"~~", ...}
							
							value += "<tr>"
										+ "<td>" + item.stationName + "</td>"
										+ "<td>" + item.dataTime + "</td>"
										+ "<td>" + item.khaiValue + "</td>"
										+ "<td>" + item.pm10Value + "</td>"
										+ "<td>" + item.coValue + "</td>"
										+ "<td>" + item.no2Value + "</td>"
										+ "<td>" + item.so2Value + "</td>"
										+ "<td>" + item.o3Value + "</td>"
								+ "</tr>";
						}
						
						$("#result1>tbody").html(value);
						
					},
					error : function() {
						console.log("ajax로 대기 오염 정보 불러오기 실패!");
					}
					
				});
				*/
				
				// 응답 데이터를 xml 형식으로 돌려받았을 경우
				$.ajax({
					url : "air.do",
					data : {location:$("#location").val()},
					success : data => {
						
						// xml 형식의 응답 데이터를 받았을 때
						// 1. 응답 데이터 내부에 실제 데이터가 담겨 있는 요소 (item)를 선택해서 변수에 담기
						var itemArr = $(data).find("item"); // [item, item, item, ...]
						
						// 2. 반복문을 이용해서 실제 데이터가 담긴 요소들에 접근해 동적으로 요소 만들기
						var value = "";
						itemArr.each((index, item) => {
							// console.log(item);
							// console.log($(item).find("stationName"));
							// <stationName>중구</stationName> 요소 자체에서 값만 뽑아야 함 == innerHTML
							// onsole.log($(item).find("stationName").text());
							
							value += "<tr>"
								 		+ "<td>" + $(item).find("stationName").text() + "</td>"
								 		+ "<td>" + $(item).find("dataTime").text() + "</td>"
								 		+ "<td>" + $(item).find("khaiValue").text() + "</td>"
								 		+ "<td>" + $(item).find("pm10Value").text() + "</td>"
								 		+ "<td>" + $(item).find("coValue").text() + "</td>"
								 		+ "<td>" + $(item).find("no2Value").text() + "</td>"
								 		+ "<td>" + $(item).find("so2Value").text() + "</td>"
								 		+ "<td>" + $(item).find("o3Value").text() + "</td>"
								  + "</tr>";
						});
						
						// 3. 동적으로 만들어낸 요소를 화면에 출력
						$("#result1>tbody").html(value);
						
					},
					error : () => {
						console.log("ajax로 대기오염 정보 불러오기 실패!");
					}
					
				});

			});
			
			/*
				*JavaScript의 화살표 함수 (Lamda)
				- JavaScript의 표준 ES6 버전부터 제공하는 가독성을 높여 주는 문법
				- 익명함수를 대체하는 문법 (선언적 함수에는 사용할 수 없음)
			
				[표현법]
				- 기존의 표현법
				function() {
				
					실행할 코드	
				
				}
			
				- 화살표 함수를 이용할 경우
				() => {
					
					실행할 코드

				}
				
				[ 사용 예시 ]
							기존 표현법			|			화살표함수 표현법
				======================================================================
					function () { }				|			() => { }
				----------------------------------------------------------------------
				 	function (a) { }			|			 a => { }
				----------------------------------------------------------------------
				    function (a, b) { }			|			(a, b) => { }
				----------------------------------------------------------------------
					function () {				|
						return 10;				|			() => 10
					}							|
*/
			
		});
	</script>

</body>
</html>