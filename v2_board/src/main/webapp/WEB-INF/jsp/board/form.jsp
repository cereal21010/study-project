<%--
  Created by IntelliJ IDEA.
  User: tlduf
  Date: 2021-07-25
  Time: 오후 7:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spring Boot Board</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<style>
    body {
        padding-top: 70px;
        padding-bottom: 30px;
    }
</style>

<body>
<article>
    <div class="container" role="main">
        <h2>Board Form</h2>
        <form id="boardForm">
            <div class="mb-3">
                <c:set var="board" value="${board}"></c:set>    <%--scope 영역 확인--%>
                <c:if test="${not empty board}"><input type="hidden" name="seq" id="seq" value="${board.seq}" > </c:if>
                <label for="title">제목</label>
                <input type="text" class="form-control" name="title" id="title" placeholder="제목을 입력해 주세요"
                       <c:if test="${not empty board}">value="${board.title}" </c:if>>
            </div>

            <div class="mb-3">
                <label for="writer">작성자</label>
                <input type="text" class="form-control" name="writer" id="writer" placeholder="이름을 입력해 주세요"
                       <c:if test="${not empty board}">value="${board.writer}" </c:if> disabled>
            </div>

            <div class="mb-3">
                <label for="content">내용</label>
                <textarea class="form-control" rows="5" name="content" id="content" placeholder="내용을 입력해 주세요"
                       > <c:if test="${not empty board}">${board.content} </c:if> </textarea>
            </div>

            <div class="mb-3">
                <label for="category">카테고리</label>

                <select class="form-control form-control-sm" name="category" id="category">
                    <option value="free">free</option>
                    <option value="question">question</option>
                    <option value="community">community</option>
                </select>
            </div>

            <div class="mb-3">
                <c:if test="${files.size() <= 0}">
                    <label for="files">첨부파일</label>
                </c:if>
                <input type="file" class="form-control" name="files" id="files" multiple>
                <c:if test="${files.size() > 0}">
                    <div id="fileList" class="form-group" style="border: 1px solid #dbdbdb;">
                        <c:forEach var="file" items="${files}">
                            <div>
                                <span> ${file.originalName} &nbsp </span>
                                <button id="${file.seq}" type='button' class="btn btn-warning btn-circle" onclick="deleteFile(this, ${file.seq})"><i class="fa fa-times">X</i></button>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>

        </form>

        <div >
            <c:choose>
                <c:when test="${not empty board}">
                    <button type="button" class="btn btn-sm btn-primary" id="btnEdit">저장</button>
                    <button type="button" class="btn btn-sm btn-primary" id="btnCancel">취소</button>
                </c:when>
                <c:otherwise>
                    <button type="button" class="btn btn-sm btn-primary" id="btnRegister">등록</button>
                    <button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>
                </c:otherwise>
            </c:choose>


        </div>
    </div>
</article>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    let main = {
        init : function() {
            let _this = this;

            $('#btnRegister').on('click', function () {
                _this.save();
            });
            $('#btnEdit').on('click', function () {
                _this.edit();
            });
            $('#btnList').on('click', function () {
                window.location = _this.urlFunction('/board/list');
            });
            $('#btnCancel').on('click', function () {
                window.location = _this.urlFunction('/board/content/${board.seq}');
            });
        },

        deleteFileList : new Array(),

        urlFunction : function(url){
            url += '?pageNum='+'${searchDTO.pageNum}';
            url += '&contentNum='+'${searchDTO.contentNum}';
            url += '&searchType='+'${searchDTO.searchType}';
            url += '&keyword='+'${searchDTO.keyword}';
            url += '&sort='+'${searchDTO.sort}';
            url += '&order='+'${searchDTO.order}';
            return url;
        },

        save : function(){

            let form = $('#boardForm')[0];

            fileList = $('#files')[0].files;
            for( let i=0; i < fileList.length; i++ ){
                if( !checkExtension(fileList[i].name) ) {
                    return false;
                }
            }

            $.ajax({
                type: 'POST',
                url: '/api/board/register',
                data: new FormData(form),
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false
            }).done(function (reponse){
                console.log('success');
                alert('글이 등록되었습니다.');
                window.location.href = '/board/list'
            }).fail(function (error){
                console.log('fail');
                alert(JSON.stringify(error));
            });
        },

        edit : function(){
            let formData = new FormData( $('#boardForm')[0] );
            formData.append('deleteFileList', main.deleteFileList);

            fileList = $('#files')[0].files;
            for( let i=0; i < fileList.length; i++ ){
                if( !checkExtension(fileList[i].name) ) {
                    return false;
                }
            }

            $.ajax({
                type: 'POST',
                url: '/api/board/update',
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                cache: false
            }).done(function (reponse){
                console.log('success');
                alert('글이 수정되었습니다.');
                window.location.href = '/board/content/${board.seq}'
            }).fail(function (error){
                console.log('fail');
                alert(JSON.stringify(error));
            });
        },

        fileCheck : function(){

            fileList = $('#files')[0].files;

            for( let i=0; i < fileList.length; i++ ){
                if( !checkExtension(fileList[i].name) ) {
                    return false;
                }
            }
            return true;
        }

    };
    deleteFile = function(_this, seq){
        main.deleteFileList.push(seq)
        _this.parentNode.remove();
    }

    checkExtension = function (fileName){
        let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
        if( regex.test(fileName) ) {
            alert('해당 종류의 파일은 업로드할 수 없습니다.(' + fileName + ')');
            return false;
        }
        return true
    }

    document.addEventListener('DOMContentLoaded', () => {
        if(${not empty board} ){
            $('#category').val('${board.category}').prop("selected", true);
        }
    });

    main.init();
</script>
</body>
</html>
