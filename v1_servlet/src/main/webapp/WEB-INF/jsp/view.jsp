<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="vo.DashboardVO" %>
<%
    DashboardVO boardDetail = (DashboardVO) request.getAttribute("boardDetail");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width" initial-scale="1">
    <title>test_dashboard</title>
</head>
<body>
<div class="container">
    <div class="row">
        <table class="table table-striped"
               style="text-align: center; border: 1px solid #dddddd">
            <thead>
            <tr>
                <th colspan="3"
                    style="background-color: #eeeeee; text-align: center;">글 보기
                </th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width: 20%;"> 글 제목</td>
                <td colspan="2"><%=boardDetail.getTitle() %>
                </td>
            </tr>
            <tr>
                <td>작성일</td>
                <td colspan="2"><%=boardDetail.getCreatedDate() %>
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td colspan="2" style="min-height: 200px; text-align: left;"><%=boardDetail.getContents() %>
                </td>
            </tr>

            </tbody>
        </table>
        <a href="javascript:history.back()">뒤로가기</a>
    </div>

</div>
</body>
</html>