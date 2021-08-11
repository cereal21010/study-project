<%--
  Created by IntelliJ IDEA.
  User: tlduf
  Date: 2021-07-25
  Time: 오전 12:12
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
    <div class="container">

        <div class="table-responsive">

            <h2>Board List</h2>

            <table class="table table-striped table-sm">
                <colgroup>
                    <col style="width:5%;" />
                    <col style="width:auto;" />
                    <col style="width:15%;" />
                    <col style="width:20%;" />
                    <col style="width:20%;" />
                </colgroup>
                <thead>
                    <tr>
                        <th class="column" id="seq">NO</th>
                        <th class="column" id="title">제목</th>
                        <th class="column" id="writer">작성자</th>
                        <th class="column" id="createdDate">작성일</th>
                        <th class="column" id="modifiedDate">수정일</th>
                    </tr>
                </thead>
                <tbody>
                <%--<tr><td colspan="5" align="center">데이터가 없습니다.</td></tr>--%>
                <c:choose>
                    <c:when test="${boardList.size() > 0}">
                        <c:forEach var="board" items="${boardList}">
                            <tr>
                                <td><c:out value="${board.seq}"/></td>
                                <td><a href="#;" onclick="main.moveContent(${board.seq})"><c:out value="${board.title}"/></a></td>
                                <td><c:out value="${board.writer}"/></td>
                                <td><c:out value="${board.createdDateConvert()}"/></td>
                                <td><c:out value="${board.modifiedDateConvert()}"/></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5" style="text-align: center">조회된 게시글이 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>

            <button type="button" class="btn btn-sm btn-primary" id = "writeBtn" >글쓰기</button>
        </div>
        <!-- pagination{s} -->

        <div id="paginationBox" style="display: inline-block">
            <ul class="pagination">
                <c:if test="${pm.prev}">
                    <li class="page-item"><a class="page-link" href="/board/list?pageNum=${pm.startPage - 1}" >Previous</a></li>
                </c:if>

                <c:forEach begin="${pm.startPage}" end="${pm.endPage}" var="idx">
                    <li class="page-item <c:out value="${(pm.pageNum+1) == idx ? 'active' : ''}"/>"><a class="page-link" href="#;" onclick="main.movePage(${idx})"> ${idx} </a></li>
                </c:forEach>

                <c:if test="${pm.next}">
                    <li class="page-item"><a class="page-link" href="/board/list?pageNum=${pm.endPage + 1}">Next</a></li>
                </c:if>
            </ul>
        </div>
        <!-- pagination{e} -->
        <div class="w100" style="display: inline-block">
            <input type="text" class="form-control form-control-sm" name="contentNum" id="contentNum">
        </div>
        <!-- search{s} -->
        <div class="form-group row justify-content-center">
            <div class="w100" style="padding-right:10px">
                <select class="form-control form-control-sm" name="searchType" id="searchType">
                    <option value="title">제목</option>
                    <option value="content">본문</option>
                    <option value="writer">작성자</option>
                </select>
            </div>
            <div class="w300" style="padding-right:10px">
                <input type="text" class="form-control form-control-sm" name="keyword" id="keyword">
            </div>
            <div>
                <button class="btn btn-sm btn-primary" name="btnSearch" id="btnSearch">검색</button>
            </div>
        </div>
        <!-- search{e} -->
    </div>
</article>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script>

    let main = {
        init : function(){
            let _this = this;
            $('.column').on('click', function () {
                let sort = $(this).attr('id');
                let order = '${searchDTO.order}'
                if( order === '' || order === 'asc' ){
                    order = 'desc';
                }else {
                    order = 'asc'
                }
                window.location = _this.urlFunction('/board/list', sort, order);
            });
            $('#writeBtn').on('click', function (){
                window.location = _this.urlFunction('/board/form');
            });
            $('#btnSearch').on('click', function(){
                window.location = _this.urlFunction('/board/list',undefined,undefined,1);
            });
        },

        movePage : function( pageNum ){
            let url = '/board/list';
            url += '?contentNum='+$('#contentNum').val();
            url += '&pageNum='+pageNum;
            if($('#keyword').val() !== '') {
                url += '&searchType=' + $('#searchType option:selected').val();
                url += '&keyword=' + $('#keyword').val();
            }
            window.location = main.urlFunction('/board/list',undefined,undefined,pageNum);
        },

        moveContent : function(seq){
            window.location = main.urlFunction('/board/content/'+seq);
        },

        //query string 함수
        urlFunction : function(url, sort, order, pageNum){
            if( pageNum !== undefined ){
                url += '?pageNum='+pageNum;
            } else{
                if( '${searchDTO.pageNum}' !== '' ){
                    url += '?pageNum='+'${searchDTO.pageNum}';
                }
            }
            url += '&contentNum='+$('#contentNum').val();
            if( $('#keyword').val() !== undefined ){
                url += '&searchType=' + $('#searchType option:selected').val();
                url += '&keyword=' + $('#keyword').val();
            } else{
                if( '${searchDTO.keyword}' !== '' ){
                    url += '&searchType=' + '${searchDTO.searchType}';
                    url += '&keyword=' + '${searchDTO.keyword}';
                }
            }
            if( sort !== undefined ) {
                url += '&sort=' + sort;
                url += '&order=' + order;
            } else{
                if( '${searchDTO.sort}' !== '' ){
                    url += '&sort=' + '${searchDTO.sort}';
                    url += '&order=' + '${searchDTO.order}';
                }
            }
            return url;
        }
    }

    //초기화..  =$(document).ready(function(){ //실행될 코드 });
    document.addEventListener('DOMContentLoaded', () => {
        $('#contentNum').val('${searchDTO.contentNum}');
        if(${not empty searchDTO.keyword} ){
            $('#keyword').val('${searchDTO.keyword}');
            $('#searchType').val('${searchDTO.searchType}').prop("selected", true);
        }
    });

    main.init();
</script>


</body>
</html>
