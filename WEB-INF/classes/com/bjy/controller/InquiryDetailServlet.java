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
import com.bjy.dao.CommentDAO;
import com.bjy.dto.BoardDTO;
import com.bjy.dto.CommentDTO;

/**
 * Servlet implementation class InquiryDetailServlet
 */
@WebServlet(urlPatterns= {"/inquiry/inquirydetail","/login/inquirydetail", "/join/inquirydetail", "/room/inquirydetail", "/reservation/inquirydetail", "/user/inquirydetail"})
public class InquiryDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * inquiry - inquiryList.jsp에서 해당 글 제목 클릭시 글에 대한 모든 정보를 저장하여 inquiryDetail.jsp에게 반환
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int b_number = Integer.parseInt(request.getParameter("b_number"));
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.getBorad(b_number);
		
		CommentDAO cdao = new CommentDAO();
		ArrayList<CommentDTO> calist = cdao.selectComment(b_number);
		
		boolean flag = false;
		HttpSession session = request.getSession();
		String u_idKey = (String)session.getAttribute("u_idKey");
		if(dto.getB_writer().equals(u_idKey)) {
			flag = true;
		}
		request.setAttribute("dto",dto);
		request.setAttribute("flag",flag);
		request.setAttribute("calist", calist);
		
		RequestDispatcher rd = request.getRequestDispatcher("/inquiry/inquiryDetail.jsp");
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
