package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DashboardService;
import utills.page.PageMaker;
import vo.DashboardVO;

/**
 * Servlet implementation class mainServlet
 */
@WebServlet(value = {"/main", "/view" } )
public class mainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DashboardService dashboardService = null;
       

    public mainServlet() {
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI();
		String seq = request.getParameter("seq");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String pageNum = request.getParameter("pageNum");
		
		String html = "";
		response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        if( path.indexOf("main") > -1 ) {
        	html = boardList(pageNum);
        } else if( path.indexOf("view") > -1 ) {
        	html = boardView(seq);
        }
        
        out.println(html);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	private String boardList(String pageNum) {
		
		if( this.dashboardService == null ) {
			this.dashboardService = new DashboardService();
		}
		
		List<DashboardVO> boardList = dashboardService.selectBoard( (pageNum == null) ? 1 : Integer.parseInt(pageNum), 10 );
		PageMaker pm = dashboardService.getPageMaker( (pageNum == null) ? 1 : Integer.parseInt(pageNum), 10);
		
		StringBuffer html = new StringBuffer();
		html.append("<div class=\"container\">");
		html.append("<div class = \"row\">");
		html.append("<table class=\"table table-striped\" style=\"text-align:center; border:1px solid #dddddd\">");
		html.append("<thead>");
		html.append("<tr>");
		html.append("<th style=\"background-color: #eeeeee; text-align: center;\">제목</th>");
		html.append("<th style=\"background-color: #eeeeee; text-align: center;\">작성자</th>");
		html.append("<th style=\"background-color: #eeeeee; text-align: center;\">작성일</th>");
		html.append("</tr>");
		html.append("</thead>");
		html.append("<tbody>");
		for( DashboardVO vo : boardList ) {
			html.append("<tr>");
			html.append("<td><a href=\" /view?seq=" + vo.getSeq() + " \"/>" + vo.getTitle() + "</td>");
			html.append("<td>" + vo.getWriter() + "</td>");
			html.append("<td>" + vo.getCreatedDate() + "</td>");
			html.append("</tr>");
		}
		html.append("</tbody>");
		html.append("</table>");
		html.append("</div>");
		//페이징
		html.append("<div class=\"row\">");
		if( pm.isPrev() ) {
			html.append("<a href=\"/main?pageNum=" + (pm.getStartPage() - 1) + "\">&lt;</a>");
		}
		for( int i = pm.getStartPage(); i <= pm.getEndPage(); i++ ) {
			html.append("<a href=\"/main?pageNum=" + i + "\">" + i + "</a>");
		}
		if( pm.isNext() ) {
			html.append("<a href=\"/main?pageNum=" + (pm.getEndPage() + 1) + "\">&gt;</a>");
		}
		html.append("</div>");
		//페이징[end]
		html.append("</div>");

		
		return html.toString();
	}
	
	
	private String boardView(String seq) {
		
		if( this.dashboardService == null ) {
			this.dashboardService = new DashboardService();
		}
		
		DashboardVO vo = dashboardService.getBoardVO( Integer.parseInt(seq) );
		
		StringBuffer html = new StringBuffer();
		html.append("<div class=\"container\">");		
		html.append("<div class=\"row\">");   
		html.append("<table class=\"table table-striped\" style=\"text-align: center; border: 1px solid #dddddd\">");   
		html.append("<thead>");   
		html.append("<tr>");   
		html.append("<th colspan=\"3\" style=\"background-color: #eeeeee; text-align: center;\">글 보기 </th>");   
		html.append("</tr>");   
		html.append("</thead>");   
		html.append("<tbody>");   
		html.append("<tr>");   
		html.append("<td style=\"width: 20%;\"> 글 제목 </td>");   
		html.append("<td colspan=\"2\">" + vo.getTitle() + "</td>");   
		html.append("</tr>");   
		html.append("<tr>");   
		html.append("<td>작성일</td>");   
		html.append("<td colspan=\"2\">" + vo.getModifiedDate() + "</td>");   
		html.append("</tr>");   
		html.append("<tr>");   
		html.append("<td>내용</td>");   
		html.append("<td colspan=\"2\" style=\"min-height: 200px; text-align: left;\">" + vo.getContents() + "</td>");   
		html.append("</tr>");   
		html.append("</tbody>");   
		html.append("</table>");  
		html.append("<a href=\"javascript:history.back()\">뒤로가기</a>"); 
		html.append("</div>");   
		html.append("</div>");  
		return html.toString();
	}

}
