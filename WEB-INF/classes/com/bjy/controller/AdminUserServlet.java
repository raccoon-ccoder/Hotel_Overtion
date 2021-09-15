package com.bjy.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjy.dao.UserDAO;
import com.bjy.dto.UserDTO;

/**
 * Servlet implementation class AdminUserServlet
 */
@WebServlet("/admin/adminUser")
public class AdminUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * admin - header.jsp에서 회원조회 클릭시 모든 회원정보를 저장하여 전달하는 서블릿
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO dao = new UserDAO();
		ArrayList<UserDTO> dto = dao.selectUser();
		request.setAttribute("dto", dto);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/checkUser.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
