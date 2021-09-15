package com.bjy.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjy.dao.ReservationDAO;

/**
 * Servlet implementation class UserReserveCancelServlet
 */
@WebServlet(urlPatterns= {"/inquiry/reservecancel","/join/reservecancel","/login/reservecancel","/reservation/reservecancel","/room/reservecancel","/user/reservecancel"})
public class UserReserveCancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * user - reserveCancel.jsp에서 체크된 예약내역 번호들을 가져와서 db에 저장된 예약 내역을 삭제 후 reserveInfo.jsp로 이동
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] r_number = request.getParameterValues("reserve");
		int[] r_numbers = Arrays.stream(r_number).mapToInt(Integer::parseInt).toArray();
		
		ReservationDAO rdao = new ReservationDAO();
		int num = rdao.cancelReservation(r_numbers);
		request.setAttribute("num", num);

		RequestDispatcher rd = request.getRequestDispatcher("reserveinfo");
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
