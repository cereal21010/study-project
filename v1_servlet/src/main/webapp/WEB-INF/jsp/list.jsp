<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="vo.DashboardVO" %>
<%@ page import="utills.page.PageMaker" %>
<%
    List<DashboardVO> boardList = (List) request.getAttribute("boardList");
    PageMaker pm = (PageMaker) request.getAttribute("pm");
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
        <table class="table table-striped" style="text-align:center; border:1px solid #dddddd">
            <thead>
            <tr>
                <th style="background-color: #eeeeee; text-align: center;">제목</th>
                <th style="background-color: #eeeeee; text-align: center;">작성자</th>
                <th style="background-color: #eeeeee; text-align: center;">작성일</th>
            </tr>
            </thead>
            <%
                for (DashboardVO board : boardList) {
            %>
            <tbody>
            <tr>
                <td><a href="/board/view?seq=<%=board.getSeq()%>"><%=board.getTitle() %>
                </a></td>
                <td><%=board.getWriter() %>
                </td>
                <td><%=board.getCreatedDate()%>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
    <!-- 페이징 -->
    <div class="row">
        <% if (pm.isPrev()) { %>
        <a href="/board/list?pageNum=<%=pm.getStartPage() - 1 %>">&lt;</a>
        <% } %>
        <% for (int i = pm.getStartPage(); i <= pm.getEndPage(); i++) { %>
        <a href="/board/list?pageNum=<%=i %>"><%=i %>
        </a>
        <% } %>
        <% if (pm.isNext()) { %>
        <a href="/board/list?pageNum=<%=pm.getEndPage() + 1 %>">&gt;</a>
        <% } %>
    </div>
</div>
</body>
</html>