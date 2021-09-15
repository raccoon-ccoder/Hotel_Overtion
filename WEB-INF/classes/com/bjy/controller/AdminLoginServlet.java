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
 * admin - admin.login.jsp에서 로그인 확인하는 서블릿 
 */
@WebServlet("/admin/adminlogin")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * admin - admin.login.jsp에서 로그인 확인 후 결과에 따른 다른 페이지로 이동하는 서블릿 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String u_id = request.getParameter("u_id");
		String u_pwd = request.getParameter("u_pwd");
		
		UserDAO dao = new UserDAO();
		int result = dao.loginAdmin(u_id, u_pwd);
		if(result==1) {
			HttpSession session = request.getSession();
			session.setAttribute("u_idKey", u_id);
			AdminReserveServlet ars = new AdminReserveServlet();
			ars.doGet(request, response);
		}else {
			request.setAttribute("msg", "로그인 실패");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
