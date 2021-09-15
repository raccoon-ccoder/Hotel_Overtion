package com.bjy.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjy.dao.RoomDAO;
import com.bjy.dto.RoomDTO;

/**
 * Servlet implementation class AdminRoomServlet
 */
@WebServlet("/admin/adminRoom")
public class AdminRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * admin - header.jsp에서 객실 수정 클릭시 모든 객실에 대한 정보를 가져와 updateRoom.jsp에게 전달 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RoomDAO dao = new RoomDAO();
		ArrayList<RoomDTO> dto = dao.selectRoom();
		request.setAttribute("dto", dto);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/updateRoom.jsp");
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
