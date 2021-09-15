package com.bjy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjy.dao.CommentDAO;

/**
 * Servlet implementation class AdminCommentWriteServlet
 */
@WebServlet("/admin/AdminCommentWrite")
public class AdminCommentWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int b_number = Integer.parseInt(request.getParameter("b_number"));
		String c_writer = "admin";
		String c_comment = request.getParameter("c_comment");
		
		CommentDAO dao = new CommentDAO();
		dao.insertComment(b_number, c_writer, c_comment);
		
		RequestDispatcher rd = request.getRequestDispatcher("adminDetail");
		rd.forward(request, response);
	}

}
