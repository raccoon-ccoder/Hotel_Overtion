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
import com.bjy.dao.CommentDAO;
import com.bjy.dto.BoardDTO;
import com.bjy.dto.CommentDTO;

/**
 * Servlet implementation class AdminInquiryDetailServlet
 */
@WebServlet("/admin/adminDetail")
public class AdminInquiryDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * admin - inquiryList.jsp에서 글 제목 클릭시 해당 글 정보를 inquiryDetail.jsp에게 전달
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int b_number = Integer.parseInt(request.getParameter("b_number"));
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.getBorad(b_number);
		
		CommentDAO cdao = new CommentDAO();
		ArrayList<CommentDTO> calist = cdao.selectComment(b_number);
		
		request.setAttribute("dto",dto);
		request.setAttribute("calist", calist);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/inquiryDetail.jsp");
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
