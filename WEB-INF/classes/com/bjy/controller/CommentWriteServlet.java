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
 * 고객 후기 게시판에서 작성한 댓글을 DB에 반영해주는 서블릿 
 */
@WebServlet(urlPatterns= {"/login/CommentWrite","/join/CommentWrite","/inquiry/CommentWrite","/reservation/CommentWrite","/room/CommentWrite","/user/CommentWrite"})
public class CommentWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * inquiryDetail.jsp에서 댓글 작성할 시 db에 댓글 저장후 다시 inquiryDetail.jsp로 이동 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int b_number = Integer.parseInt(request.getParameter("b_number"));
		String c_writer = request.getParameter("c_writer");
		String c_comment = request.getParameter("c_comment");
		
		CommentDAO dao = new CommentDAO();
		dao.insertComment(b_number, c_writer, c_comment);
		
		RequestDispatcher rd = request.getRequestDispatcher("inquirydetail");
		rd.forward(request, response);
	}

}
