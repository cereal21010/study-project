package servlet.concreteCommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DashboardService;
import servlet.command.BoardView;
import servlet.command.Command;
import vo.DashboardVO;

public class BoardViewCommand implements Command{


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String seq = request.getParameter("seq");
			
			DashboardService dashboardService = new DashboardService();
			
			DashboardVO boardDetail = dashboardService.getBoardVO( Integer.parseInt(seq) );
			request.setAttribute("boardDetail", boardDetail);
			
			request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
			
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
