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

import com.bjy.dao.ReservationDAO;
import com.bjy.dto.ReservationDTO;

/**
 * Servlet implementation class UserReserveInfoServlet
 */
@WebServlet(urlPatterns= {"/user/reserveinfo","/room/reserveinfo","/reservation/reserveinfo","/login/reserveinfo","/join/reserveinfo","/inquiry/reserveinfo"})
public class UserReserveInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 각 header.jsp에서 예약내역 클릭시 회원의 예약내역 정보를 reservation - reserveInfo.jsp에게 전달 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String u_idKey = (String)session.getAttribute("u_idKey");
		ReservationDAO dao = new ReservationDAO();
		ArrayList<ReservationDTO> dto = dao.selectReservation(u_idKey);

		request.setAttribute("dto", dto);
		RequestDispatcher rd = request.getRequestDispatcher("/user/reserveInfo.jsp");
		rd.forward(request, response);
	}

	/**
	 * user - reserveInfo.jsp에서 예약취소 버튼 클릭시 회원의 모든 예약 정보를 reserveCancel.jsp에게 전달
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String u_idKey = (String)session.getAttribute("u_idKey");
		ReservationDAO dao = new ReservationDAO();
		ArrayList<ReservationDTO> dto = dao.selectReservation(u_idKey);
		
		request.setAttribute("dto", dto);
		RequestDispatcher rd = request.getRequestDispatcher("/user/reserveCancel.jsp");
		rd.forward(request, response);
	}

}
