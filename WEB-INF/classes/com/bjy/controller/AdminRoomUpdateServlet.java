package com.bjy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjy.dao.RoomDAO;
import com.bjy.dto.RoomDTO;

/**
 * Servlet implementation class AdminRoomUpdateServlet
 */
@WebServlet("/admin/roomUpdate")
public class AdminRoomUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * admin - updateRoom.jsp에서 해당 객실의 수정 버튼 클릭시 수정할 객실 정보를 db에서 가져와 updateRoomInfo.jsp에게 전달
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String room_type = request.getParameter("room_type");
		RoomDAO dao = new RoomDAO();
		RoomDTO dto = dao.selectRoomType(room_type);
		request.setAttribute("dto", dto);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/updateRoomInfo.jsp");
		rd.forward(request, response);
	}

	/**
	 * updateRoomInfo.jsp에서 수정한 객실 정보를 전달받아 db에 수정 후 updateRoom.jsp로 이동 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String room_type = request.getParameter("room_type");
		int room_id = Integer.parseInt(request.getParameter("room_id"));
		int room_size = Integer.parseInt(request.getParameter("room_size"));
		int room_capacity = Integer.parseInt(request.getParameter("room_capacity"));
		int room_price = Integer.parseInt(request.getParameter("room_price"));
		int room_count = Integer.parseInt(request.getParameter("room_count"));
		
		RoomDTO dto = new RoomDTO();
		dto.setRoom_capacity(room_capacity);
		dto.setRoom_count(room_count);
		dto.setRoom_id(room_id);
		dto.setRoom_price(room_price);
		dto.setRoom_size(room_size);
		dto.setRoom_type(room_type);
		
		RoomDAO dao = new RoomDAO();
		dao.updateRoom(dto);
		
		RequestDispatcher rd = request.getRequestDispatcher("adminRoom");
		rd.forward(request, response);
	}

}
