package servlet.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DashboardService;
import vo.DashboardVO;

public class BoardView {
	
	public void openPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String seq = request.getParameter("seq");
		
		DashboardService dashboardService = new DashboardService();
		
		DashboardVO boardDetail = dashboardService.getBoardVO( Integer.parseInt(seq) );
		request.setAttribute("boardDetail", boardDetail);
		
		request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
		
	}

}
