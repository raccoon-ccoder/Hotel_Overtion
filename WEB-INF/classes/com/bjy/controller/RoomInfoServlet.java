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
 * 각 파일의 header에서 객실 정보 클릭시 각 객실에 대한 정보를 전달하는 서블릿
 */
@WebServlet(urlPatterns= {"/join/roomInfo","/user/roomInfo","/inquiry/roomInfo","/login/roomInfo","/reservation/roomInfo","/room/roomInfo"})
public class RoomInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String room_type = request.getParameter("room_type");
		RoomDAO dao = new RoomDAO();
		RoomDTO dto = null;
		dto = dao.selectRoomType(room_type);
		
		request.setAttribute("dto", dto);
		RequestDispatcher rd = request.getRequestDispatcher("../room/"+room_type+".jsp");
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
