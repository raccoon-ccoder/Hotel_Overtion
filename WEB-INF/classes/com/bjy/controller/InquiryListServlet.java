package com.bjy.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bjy.dao.BoardDAO;
import com.bjy.dto.BoardDTO;

/**
 * Servlet implementation class InquiryListServlet
 */
@WebServlet(urlPatterns= {"/inquiry/inquirylist", "/join/inquirylist","/login/inquirylist", "/user/inquirylist", "/room/inquirylist", "/reservation/inquirylist"})
public class InquiryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * inquiry 폴더 내의 header 고객문의 <a> 태그 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<BoardDTO> balist = null;
		BoardDAO bDAO = new BoardDAO();
		balist = bDAO.getBoradList();
		request.setAttribute("balist", balist);
		RequestDispatcher rd = request.getRequestDispatcher("/inquiry/inquiryList.jsp");
		rd.forward(request, response);
	}

	/**
	 * inquiry - inquiryWrite.jsp에서 글 작성 후 db에 저장하고 inquiryList.jsp 이동
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String b_writer = (String)session.getAttribute("u_idKey");
		String b_title = request.getParameter("b_title");
		String b_content = request.getParameter("b_content");
		BoardDAO b = new BoardDAO();
		
		b.insertBoard(b_title, b_writer, b_content);
		
		doGet(request, response);
	}

}
