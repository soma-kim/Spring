<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <style>
        #updateForm>table {width:100%;}
        #updateForm>table * {margin:5px;}
    </style>
</head>
<body>
        
    <jsp:include page="../common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 수정하기</h2>
            <br>

            <form id="updateForm" method="post" action="update.bo" enctype="multipart/form-data">
                <input type="hidden" name="boardNo" value="${ b.boardNo }">
                <table algin="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" value="${ b.boardTitle }" name="boardTitle" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${ b.boardWriter }" name="boardWriter" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td>
                            <input type="file" id="upfile" class="form-control-file border" name="reupfile">
                         	
                         	<!--
                         		c:if 태그의 test 속성에는 originName, changeName 중 무엇이 들어가도 상관없음!
                         		어차피 둘 중에 하나라도 있으면 첨부파일 있는 거고, 없으면 둘 다 없을 것이기 때문에!
                         	-->
                         	<c:if test="${ not empty b.originName }">
	                         	현재 업로드된 파일 : 
	                            <a href="${ b.changeName }" download="${ b.originName }">${ b.originName }</a>
	                            
	                            <!-- 수정을 하기 위해서는 만약 기존 파일이 있었다면 기존 파일에 대한 정보도 넘겨 줘야 함! -->
	                            <input type="hidden" name="originName" value="${ b.originName }">
	                            <input type="hidden" name="changeName" value="${ b.changeName }">
                         	</c:if>

                        </td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="boardContent" required>${ b.boardContent }</textarea></td>
                    </tr>
                </table>
                <br>

                <div align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <button type="button" class="btn btn-danger" onclick="javascript:history.go(-1);">이전으로</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>