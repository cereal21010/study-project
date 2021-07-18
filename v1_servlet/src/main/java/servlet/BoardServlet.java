package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet.command.Command;
import servlet.concreteCommand.BoardListCommand;
import servlet.concreteCommand.BoardViewCommand;

@WebServlet( value = {"/board/list", "/board/view"} )
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Command> commandMap;
	
    public BoardServlet() {
    	
        super();
        commandMap = new HashMap<>();
        commandMap.put("list", new BoardListCommand());
        commandMap.put("view", new BoardViewCommand());
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("== do Get ==");
		
		String path = request.getRequestURI();

		//분기하는방법 업데이트 하기 ( list만으로 게시판 리스트 페이지를 띄워주는건 무책임하다.. )
		//spring
		if( "/board/list".equals(path) ) {
			doService("list", request, response);
			
		}
		else if( "/board/view".equals(path) ) {
			doService("view", request, response);
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void doService(String requestUri, HttpServletRequest request, HttpServletResponse response) {
		commandMap.get(requestUri).execute(request, response);
	}

}
