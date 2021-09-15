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
 * Servlet implementation class AdminInquiryDeleteServlet
 */
@WebServlet("/admin/inquiryDelete")
public class AdminInquiryDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * admin - inquiryDetail.jsp에서 삭제 버튼 클릭시 해당 게시물을 삭제 후 inquiryList.jsp로 이동
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int b_number = Integer.parseInt(request.getParameter("b_number"));
		BoardDAO dao = new BoardDAO();
		dao.deleteBoard(b_number);
		
		CommentDAO cdao = new CommentDAO();
		cdao.deleteAllComment(b_number);
		
		RequestDispatcher rd = request.getRequestDispatcher("adminInquiry");
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
