package com.bjy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bjy.dao.UserDAO;
import com.bjy.dto.UserDTO;

/**
 * 로그인 했을때 각 파일의 header.jsp에서 고객정보 클릭시 이동하여 고객의 정보를 userInfo.jsp에게 이동
 */
@WebServlet(urlPatterns= {"/user/userinfo","/room/userinfo","/reservation/userinfo","/login/userinfo","/inquiry/userinfo","/join/userinfo"})
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 로그인 했을때 각 파일의 header.jsp에서 고객정보 클릭시 이동하여 고객의 정보를 userInfo.jsp에게 이동
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String u_idKey = (String)session.getAttribute("u_idKey");
		UserDAO dao = new UserDAO();
		UserDTO dto = null;
		
		dto = dao.selectUser(u_idKey);
		request.setAttribute("dto", dto);
		
		RequestDispatcher rd = request.getRequestDispatcher("/user/userInfo.jsp");
		rd.forward(request, response);
	}

	/**
	 * user - userUpdate.jsp에서 수정 회원 정보를 가져와 db에 회원 정보를 수정하여 userInfo.jsp로 이동하는 메서드
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String u_id = request.getParameter("u_id");
		String u_pwd = request.getParameter("u_pwd");
		String u_name = request.getParameter("u_name");
		String u_phone = request.getParameter("u_phone");
		String u_email = request.getParameter("u_email");
		
		UserDTO udto = new UserDTO();
		udto.setU_id(u_id);
		udto.setU_pwd(u_pwd);
		udto.setU_name(u_name);
		udto.setU_phone(u_phone);
		udto.setU_email(u_email);
		
		UserDAO udao = new UserDAO();
		int result = udao.updateUser(udto);
		request.setAttribute("result", result);
		
		doGet(request, response);
	}

}
