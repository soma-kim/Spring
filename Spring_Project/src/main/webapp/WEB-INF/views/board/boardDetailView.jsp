<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <style>
        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
    <jsp:include page="../common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="list.bo">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <!-- 여기서 잘못 넣어 주면 getter 오류 남! -->
                    <td colspan="3">${ b.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${ b.boardWriter }</td>
                    <th>작성일</th>
                    <td>${ b.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    	<c:choose>
                    		<c:when test="${ empty b.originName }">
                    			첨부파일이 없습니다.
                    		</c:when>
                    		<c:otherwise>
                    			<!-- 그냥 download로만 적어도 다운로드는 가능하지만, 그렇게 되면 수정명으로 파일 다운로드되므로 원본 파일명으로 처리해 줄 것! -->
                        		<a href="${ b.changeName }" download="${ b.originName }">${ b.originName }</a>
                    		</c:otherwise>
                    	</c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${ b.boardContent }</p></td>
                </tr>
            </table>
            <br>
            
            <c:if test="${ loginUser.userId eq b.boardWriter }">
	            <div align="center">
	                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
	                <a class="btn btn-primary" onclick="postFormSubmit(1);">수정하기</a>
	                <a class="btn btn-danger" onclick="postFormSubmit(2);">삭제하기</a>
	            </div>
	            <br><br>
	            
	            <!-- action 속성 비워 놓고 아래 script에서 action 속성만 바꿔치기 하는 게 포인트! -->
	            <form id="postForm" action="" method="post">
	            	<input type="hidden" name="bno" value="${ b.boardNo }">
	            	<!-- input 태그의 submit 버튼도 만들지 않음! -->
	            	<input type="hidden" name="filePath" value="${ b.changeName }">
	            	<!-- 두 번 일하지 않게끔 애초에 changeName으로 넘기기! -->
	            </form>
	            
	            <script>
	            	function postFormSubmit(num) {
						
	            		// action 속성값을 부여 후 연이어서 곧바로 submit 시키기
	            		if(num == 1) { // 수정하기 버튼 클릭 시 num == 1 : updateForm.bo
	            			
	            			// location.href="updateForm.bo"; // get방식이기 때문에 url 노출됨! 우리의 목적에 적합하지 않음
	            			$("#postForm").attr("action", "updateForm.bo").submit();
	            			
	            		} else { // 삭제하기 버튼 클릭 시 num == 2 : delete.bo
	            			
	            			$("#postForm").attr("action", "delete.bo").submit();
	            			// 폼 태그 선택, action 속성을 바꾸고, 메소드 체이닝을 통해 submit
	            			
	            		}
	            	}
	            </script>
            </c:if>

            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                    
                    	<c:choose>
                    		<c:when test="${ empty loginUser }">
                    			<!-- 로그인 전 -->                 	
		                        <th colspan="2">
		                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%;" readonly>로그인한 사용자만 이용 가능한 서비스입니다. 로그인 후 이용 바랍니다.</textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button class="btn btn-secondary" disabled>등록하기</button></th>
                    		</c:when>
                    		<c:otherwise>
                    			<!-- 로그인 후 -->                 	
		                        <th colspan="2">
		                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button class="btn btn-secondary" onclick="addReply();">등록하기</button></th>
                    		</c:otherwise>
                    	</c:choose>

                    </tr>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">0</span>)</td>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <br><br>
        
        <script>
        	// 모든 요소들이 화면에 출력된 다음 바로 실행
        	$(function() {
        		selectReplyList();
        	});
        	
        	 // 해당 게시글에 딸린 댓글 리스트 조회용 ajax 요청
        	function selectReplyList() {
        		
        		 $.ajax({
        			url : "rlist.bo",
        			data : {bno: ${ b.boardNo }},
        			success : function(result) {
        				
        				// console.log(result);
        				
        				var resultStr = "";
        				
        				for(var i = 0; i < result.length; i++) {
        					
        					resultStr += "<tr>"
        									+ "<td>" + result[i].replyWriter + "</td>"
        									+ "<td>" + result[i].replyContent + "</td>"
        									+ "<td>" + result[i].createDate + "</td>"
        							   + "</tr>";
        					
        				}
        				
        				$("#replyArea>tbody").html(resultStr);
        				
        				// 댓글 개수 출력 => 기존 0에서 조회된 값으로 덮어씌우기
        				$("#rcount").text(result.length);
        				
        			},
        			error : function() {
        				console.log("댓글 리스트 조회용 ajax 통신 실패!");
        			}
        		 });
        		 
        	}
        	 
        	function addReply() { // 댓글 작성 요청용 ajax
        		
        		// form 태그 내에서는 required 속성이 적용되지만
        		// form 태그 밖에서는 required 속성이 소용 없음!
        		// => 댓글 내용이 있는지 먼저 검사 후에 있다면 ajax 요청 보내기!
        		//    (textarea 요소에 value 속성값 기준으로 공백 제거 후 길이가 0이 아닌 경우)
        		if($("#content").val().trim().length != 0) {
        			
        			$.ajax({
        				url : "rinsert.bo",
        				data : { // ajax 요청 또한 커맨드 객체 방식 가능 (키값을 필드명이랑 맞춰 줌)
        					refBoardNo:${ b.boardNo },
        					replyWriter:"${ loginUser.userId }",
        					replyContent:$("#content").val()
        				},
        				success : function(result) {
        					
        					// "success" 또는 "fail"이라는 문자열이 들어 있음
        					if(result == "success") {
        						
        						// 댓글 작성 성공 시 새로이 댓글 리스트를 불러올 것
        						// 화면이 깜빡거리지 않고 새롭게 작성한 댓글을 추가 후 리스트가 다시 보여지게 될 것
        						selectReplyList();
        						
        						// 댓글 작성창 초기화
        						$("#content").val("");
        					}
        					
        				},
        				error : function() {
        					console.log("댓글 작성용 ajax 통신 실패!");
        				}
        			});
        			
        		} else {
        			
        			alertify.alert("댓글 작성 실패!", "댓글 작성 후 등록 가능합니다.");
        			
        		}
        		
        	}
        </script>

    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>