<%--
  Created by IntelliJ IDEA.
  User: tlduf
  Date: 2021-07-29
  Time: 오후 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
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
    .board_title {
        font-weight : 700;
        font-size : 22pt;
        margin : 10pt;
    }
    .board_info_box {
        color : #6B6B6B;
        margin : 10pt;
    }
    .board_author {
        font-size : 10pt;
        margin-right : 10pt;
    }
    .board_date {
        font-size : 10pt;
    }
    .board_content {
        color : #444343;
        font-size : 12pt;
        margin : 10pt;
    }
    .board_tag {
        font-size : 11pt;
        margin : 10pt;
        padding-bottom : 10pt;
    }
</style>
<body>
<article>
    <div class="container" role="main">
        <h2>Board Content</h2>

        <div class="bg-white rounded shadow-sm">
            <div class="board_title"><c:out value="${board.title}"/></div>
            <div class="board_info_box">
                <span class="board_author"><c:out value="${board.writer}"/>,</span><span class="board_date"><c:out value="${board.modifiedDateConvert()}"/></span>
            </div>
            <div class="board_content">${board.content}</div>
            <div class="board_tag">category : <c:out value="${board.category}"/></div>
        </div>

        <div style="margin-top : 20px">
            <button type="button" class="btn btn-sm btn-primary" id="btnEdit">수정</button>
            <button type="button" class="btn btn-sm btn-primary" id="btnDelete">삭제</button>
            <button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>
        </div>
    </div>
</article>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    let main = {
        init : function() {
            let _this = this;
            $('#btnList').on('click', function () {
                window.location = _this.urlFunction('/board/list');
            });
            $('#btnDelete').on('click', function () {
                if(confirm('게시글을 삭제 하시겠습니까?')) {
                    _this.delete(_this);
                }
            });
            $('#btnEdit').on('click', function () {
                window.location = _this.urlFunction('/board/edit/${board.seq}');
            });
        },
        delete : function(_this){
            let data = {
                seq: ${board.seq}
            };

            $.ajax({
                type: 'POST',
                url: '/api/board/delete',
                data: data
            }).done(function (){
                alert('게시글이 삭제되었습니다.');
                window.location = _this.urlFunction('/board/list');
            }).fail(function (error){
                alert(JSON.stringify(error));
            });
        },
        urlFunction : function(url){
            url += '?pageNum='+'${searchDTO.pageNum}';
            url += '&contentNum='+'${searchDTO.contentNum}';
            url += '&searchType='+'${searchDTO.searchType}';
            url += '&keyword='+'${searchDTO.keyword}';
            url += '&sort='+'${searchDTO.sort}';
            url += '&order='+'${searchDTO.order}';
            return url;
        }
    };

    main.init();
</script>
</body>
</html>
