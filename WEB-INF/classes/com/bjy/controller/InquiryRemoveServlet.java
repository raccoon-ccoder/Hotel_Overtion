package com.bjy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjy.dao.BoardDAO;
import com.bjy.dao.CommentDAO;

/**
 * Servlet implementation class InquiryRemoveServlet
 */
@WebServlet(urlPatterns= {"/inquiry/inquiryremove","/join/inquiryremove","/login/inquiryremove","/reservation/inquiryremove","/room/inquiryremove","/user/inquiryremove"})
public class InquiryRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * inquiry - inquiryDetail.jsp 에서 게시글 삭제 후 inquiry.jsp로 이동하는 메서도
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int b_number = Integer.parseInt(request.getParameter("b_number"));
		BoardDAO dao = new BoardDAO();
		dao.deleteBoard(b_number);
		
		CommentDAO cdao = new CommentDAO();
		cdao.deleteAllComment(b_number);
	
		RequestDispatcher rd = request.getRequestDispatcher("inquirylist");
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
