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
<form action="/api/board/save" method="post">
        <div class="mb-3">
            <label for="title">제목</label>
            <input type="text" class="form-control" name="title" id="title" placeholder="제목을 입력해 주세요">
        </div>

        <div class="mb-3">
            <label for="writer">작성자</label>
            <input type="text" class="form-control" name="writer" id="writer" placeholder="이름을 입력해 주세요">
        </div>

        <div class="mb-3">
            <label for="content">내용</label>
            <textarea class="form-control" rows="5" name="content" id="content" placeholder="내용을 입력해 주세요" ></textarea>
        </div>

        <div class="mb-3">
            <label for="category">카테고리</label>
            <input type="text" class="form-control" name="category" id="category" placeholder="카테고리를 입력해 주세요">
        </div>
</form>
        <div >
            <button type="button" class="btn btn-sm btn-primary" id="btnSave">저장</button>
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
            $('#btnSave').on('click', function () {
                _this.save();
            });
            $('#btnList').on('click', function () {
                window.location = '/board/list'
            });
        },

        save : function(){
            let data = {
                title: $('#title').val(),
                writer: $('#writer').val(),
                category: $('#category').val(),
                content: $('#content').val()
            };

            console.log(data);

            $.ajax({
                type: 'POST',
                url: '/api/board/save',
                dataType: 'json',
                contentType: 'application/x-www-form-urlencoded; charset=utf-8',
                data: data
            }).done(function (){
                alert('글이 등록되었습니다.');
                window.location.href = '/board/list'
            }).fail(function (error){
                alert(JSON.stringify(error));
            });
        }
    };

    main.init();
</script>
</body>
</html>
