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
 * Servlet implementation class CommentUpdateServlet
 */
@WebServlet(urlPatterns= {"/inquiry/CommentUpdate","/reservation/CommentUpdate","/room/CommentUpdate","/user/CommentUpdate","/join/CommentUpdate","/login/CommentUpdate"})
public class CommentUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * inquiryDetail.jsp에서 댓글 수정 버튼 클릭시 db에 댓글 수정 반영후 다시 inquiryDetail.jsp로 돌아감 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String c_comment = request.getParameter("c_comment");
		int c_number = Integer.parseInt(request.getParameter("c_number"));
		
		CommentDAO dao = new CommentDAO();
		dao.updateComment(c_number, c_comment);

		RequestDispatcher rd = request.getRequestDispatcher("inquirydetail");
		rd.forward(request, response);
	}

}
