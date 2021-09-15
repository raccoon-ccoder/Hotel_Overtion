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
 * Servlet implementation class AdminIuquiryServlet
 */
@WebServlet("/admin/adminInquiry")
public class AdminIuquiryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * admin - header.jsp에서 후기내역 클릭시 후기내역 정보들을 저장하여 inquiryList.jsp에게 전달
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<BoardDTO> balist = null;
		BoardDAO bDAO = new BoardDAO();
		balist = bDAO.getBoradList();
		request.setAttribute("balist", balist);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/inquiryList.jsp");
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
