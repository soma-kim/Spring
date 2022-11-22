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
                        <th colspan="2">
                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                        </th>
                        <th style="vertical-align:middle"><button class="btn btn-secondary">등록하기</button></th>
                    </tr>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">3</span>)</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>user02</th>
                        <td>ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ꿀잼</td>
                        <td>2020-03-12</td>
                    </tr>
                    <tr>
                        <th>user01</th>
                        <td>재밌어요</td>
                        <td>2020-03-11</td>
                    </tr>
                    <tr>
                        <th>admin</th>
                        <td>댓글입니다!!</td>
                        <td>2020-03-10</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>