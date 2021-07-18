package servlet.concreteCommand;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DashboardService;
import servlet.command.Command;
import utills.page.PageMaker;
import vo.DashboardVO;

public class BoardListCommand implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String pageNum = request.getParameter("pageNum");
			
			DashboardService dashboardService = new DashboardService();
			
			List<DashboardVO> boardList = dashboardService.selectBoard( (pageNum == null) ? 1 : Integer.parseInt(pageNum), 10 );
			PageMaker pm = dashboardService.getPageMaker( (pageNum == null) ? 1 : Integer.parseInt(pageNum), 10);
			
			request.setAttribute("boardList", boardList);
			request.setAttribute("pm", pm);
			
			request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
			
		
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		
	}

	
}
