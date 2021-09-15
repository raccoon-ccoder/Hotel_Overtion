package com.bjy.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjy.dao.ReservationDAO;
import com.bjy.dto.ReservationDTO;

/**
 * Servlet implementation class AdminReserveServlet
 */
@WebServlet("/admin/adminReserve")
public class AdminReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 관리자 로그인 후 admin - checkReserve.jsp 이동시 예약내역 조회 정보를 전달하는 메소드
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReservationDAO dao = new ReservationDAO();
		ArrayList<ReservationDTO> result = dao.selectReserve();
		request.setAttribute("result", result);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/checkReserve.jsp");
		rd.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
