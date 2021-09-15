package com.bjy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjy.dao.UserDAO;
import com.bjy.dto.UserDTO;

/**
 * Servlet implementation class JoinServlet
 */
@WebServlet(urlPatterns= {"/join/joinUser","/login/joinUser","/user/joinUser","/room/joinUser","/reservation/joinUser","/inquiry/joinUser"})
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * join - joinForm.jsp 에서 form submit 태그로 post 방식 호출 (회원가입 정보)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserDAO dao = new UserDAO();
		UserDTO dto = new UserDTO();
		
		dto.setU_name(request.getParameter("u_name"));
		dto.setU_id(request.getParameter("u_id"));
		dto.setU_pwd(request.getParameter("u_pwd"));
		dto.setU_email(request.getParameter("u_email"));
		dto.setU_phone(request.getParameter("u_phone"));
		
		boolean flag = dao.insertUser(dto);
		request.setAttribute("flag", flag);
		request.setAttribute("u_name", request.getParameter("u_name"));
		request.setAttribute("u_id", request.getParameter("u_id"));
		RequestDispatcher rd = request.getRequestDispatcher("joinProc.jsp");
		rd.forward(request, response);
	}

}
