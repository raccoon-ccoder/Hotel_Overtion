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
 * Servlet implementation class CommentDeleteServlet
 */
@WebServlet(urlPatterns= {"/join/CommentDelete","/login/CommentDelete","/reservation/CommentDelete","/room/CommentDelete","/user/CommentDelete","/inquiry/CommentDelete"})
public class CommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * inquiryDetail.jsp에서 댓글 삭제 버튼 클릭시 db에서 댓글 date 삭제하는 서블릿 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int c_number = Integer.parseInt(request.getParameter("c_number"));
		CommentDAO dao = new CommentDAO();
		dao.deleteComment(c_number);
		
		RequestDispatcher rd = request.getRequestDispatcher("inquirydetail");
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
