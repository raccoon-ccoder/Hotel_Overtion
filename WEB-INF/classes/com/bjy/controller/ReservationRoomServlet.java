package com.bjy.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bjy.dao.ReservationDAO;
import com.bjy.dto.ReservationDTO;
import com.bjy.dto.RoomDTO;

/**
 *  - 원하는 투숙 날짜, 인원수 정보를 받아 조건에 맞는 객실 정보를 제공
 *  - 예약 정보를 DB에 저장하는 역할
 */
@WebServlet(urlPatterns = {"/reservation/reserveroom"})
public class ReservationRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * reservation - reservation1.jsp에서 원하는 투숙 날짜, 인원수 정보를 받아 reservation2.jsp에게 조건에 맞는 객실 정보를 전송
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String r_checkin = request.getParameter("r_checkin");
		String r_checkout = request.getParameter("r_checkout");
		int r_adults = Integer.parseInt(request.getParameter("r_adults"));
		int r_kids = Integer.parseInt(request.getParameter("r_kids"));	

		String msg = "";
		ReservationDAO rDAO = new ReservationDAO();
		ArrayList<RoomDTO> room_type = rDAO.selectRoomByPeople(r_adults+r_kids);
		ArrayList<RoomDTO> totalroom = rDAO.selectRoomByDate(room_type, r_checkin, r_checkout);
		
		if(totalroom.size()==0){
			msg = "예약 가능하신 방이 없습니다.다시 선택해주세요";
		}
		
		String  str = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		long diffday = 0;
		try {
			Date checkin = sdf.parse(r_checkin);
			Date checkout = sdf.parse(r_checkout);
			
			diffday = (checkout .getTime() - checkin.getTime()) / (24*60*60*1000);
		
		} catch (ParseException e) {
			System.err.println("ReservationRoomServlet - doGet() err : " + e.getMessage());
		}
		
		request.setAttribute("diffday", diffday);
		request.setAttribute("r_checkin", r_checkin);
		request.setAttribute("r_checkout", r_checkout);
		request.setAttribute("r_adults", r_adults);
		request.setAttribute("r_kids", r_kids);
		request.setAttribute("totalroom", totalroom);
		request.setAttribute("msg", msg);
		
		RequestDispatcher rd = request.getRequestDispatcher("reservation2.jsp");
		rd.forward(request, response);
		
	}
 
	/**
	 *  reservation - reservation3.jsp 에서 예약에 관련된 모든 정보를 가져와 db에 저장후 예약 완료 여부 결과를 reservationProc.jsp 에게 전달
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String r_id = (String)session.getAttribute("u_idKey");
		int r_adults = Integer.parseInt(request.getParameter("r_adults"));
		int r_kids = Integer.parseInt(request.getParameter("r_kids"));
		String r_checkin = request.getParameter("r_checkin");
		String r_checkout = request.getParameter("r_checkout");
		String r_type = request.getParameter("r_type");
		int r_price = Integer.parseInt(request.getParameter("r_price"));
		
		ReservationDTO r = new ReservationDTO();
		r.setR_id(r_id);
		r.setR_adults(r_adults);
		r.setR_kids(r_kids);
		r.setR_checkin(r_checkin);
		r.setR_checkout(r_checkout);
		r.setR_type(r_type);
		r.setR_price(r_price);

		ReservationDAO dao = new ReservationDAO();
		int result = dao.insertRoom(r);
		
		request.setAttribute("result", result);
		RequestDispatcher rd = request.getRequestDispatcher("reservationProc.jsp");
		rd.forward(request, response);
	}

}
