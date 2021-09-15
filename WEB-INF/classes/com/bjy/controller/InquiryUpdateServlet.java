package com.bjy.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjy.dao.BoardDAO;
import com.bjy.dto.BoardDTO;

/**
 * Servlet implementation class InquiryUpdateServlet
 */
@WebServlet("/inquiry/inquiryupdate")
public class InquiryUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 *  inquiry - inquiryUpdate.jsp에서 글 수정 후 inquiryList.jsp로 이동하는 메서도 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int b_number = Integer.parseInt(request.getParameter("b_number"));
		String b_title = request.getParameter("b_title");
		String b_content = request.getParameter("b_content");
		
		BoardDAO dao = new BoardDAO();
		dao.updateBoard(b_number, b_title, b_content);
		
		ArrayList<BoardDTO> balist = null;
		balist = dao.getBoradList();
		request.setAttribute("balist", balist);
		RequestDispatcher rd = request.getRequestDispatcher("inquirydetail");
		rd.forward(request, response);
	}

}
