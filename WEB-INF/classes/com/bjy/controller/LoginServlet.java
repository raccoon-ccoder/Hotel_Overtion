package com.bjy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bjy.dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = {"/login/loginUser", "/user/login.do"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/** login - login.jsp 에서 로그인시 호출되는 메서도**/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String u_id = request.getParameter("u_id");
		String u_pwd = request.getParameter("u_pwd");
		UserDAO dao = new UserDAO();
		int mode = dao.loginUser(u_id, u_pwd);
		
		if(mode==0){
			request.setAttribute("mode", mode);
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}else if(mode==1){
			request.setAttribute("mode", mode);
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}else if(mode==2){
			HttpSession session = request.getSession();
			session.setAttribute("u_idKey", u_id);
			response.sendRedirect("main.jsp");
		}
	}

}
