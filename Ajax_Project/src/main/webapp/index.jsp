<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <!-- jQuery 라이브러리 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>

	<h1>Spring에서의 AJAX 사용법</h1>
	<!-- 응답 페이지가 아닌 응답 데이터만 넘어오도록 하는 비동기 방식인 Ajax -->
	
	<h3>1. 요청 시 값 전달, 응답 결과 받아보기</h3>
	
	이름: <input type="text" id="name"> <br>
	나이: <input type="number" id="age"> <br>
	<button onclick="test1();">전송</button>
	<div id="result1"></div>
	
	<script>
		function test1() {
			
			$.ajax({
				url : "ajax1.do",
				type : "get",
				data : {
					name : $("#name").val(),
					age : $("#age").val()
				},
				success : function(result) {
					// console.log(result);
					// 응답 데이터가 한 개일 경우
					// $("#result1").text(result);
					
					// 응답 데이터가 여러 개일 경우(JSONArray로 보냈을 경우)
					/*
					var resultStr = "이름: " + result[0] + "<br>"
								  + "나이: " + result[1] + "<br>";
								  
					$("#result1").html(resultStr);
					*/
					
					// 응답 데이터가 여러 개일 경우(JSONObject로 보냈을 경우)
					// => 객체에서 속성값에 접근할 경우: 객체명.속성명
					var resultStr = "이름: " + result.name + "<br>"
					  			  + "나이: " + result.age + "<br>"; 
					
					$("#result1").html(resultStr);
					
				},
				error : function() {
					console.log("ajax 통신 실패!");
				}
			});
		}
	</script>
	
	<hr>
	
	<h3>2. 조회 요청 후 조회된 한 회원의 객체를 응답 받아서 출력해 보기</h3>
	조회할 회원번호: <input type="number" id="userNo">
	<button id="btn">조회</button>
	<br>
	<div id="result2"></div>
	
	<script>
		$(function() {
			
			$("#btn").click(function() {
				
				var userNo = $("#userNo").val();
				
				$.ajax({
					url : "ajax2.do",
					data : {userNo : userNo},
					success : function(result) {
						
						// console.log(result);
						var resultStr = "<ul>"
									  +	"<li>이름: " + result.userName + "</li>"
									  +	"<li>아이디: " + result.userId + "</li>"
									  +	"<li>나이: " + result.age + "</li>"
									  +	"<li>휴대폰: " + result.phone + "</li>"
									  + "</ul>";
									  
						$("#result2").html(resultStr);
					},
					error : function() {
						console.log("ajax 통신 실패!");
					}
				});
			});
		});
	</script>
	
	














</body>
</html>