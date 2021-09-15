package com.bjy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(urlPatterns= {"/login/info","/join/info","/user/info","/room/info","/inquiry/info","/reservation/info"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * login 폴더내의 파일들 header 중 고객정보 버튼 <a>태그
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("u_idKey") != null) {	// 로그인했을 경우
			String path = request.getServletPath();
			if(path.contains("user")) {					// user 폴더 내에서 호출할 경우
				response.sendRedirect("userInfo.jsp");	// url 주소 상대경로 : 원래 url (폴더명)에 url 추가
			}else {
				response.sendRedirect("/hotel_prj/user/userInfo.jsp");	// url 절대경로 : 포트번호 뒤부터 새로 작성
			}
		}else {	// 로그인하지 않았을 경우
			String path = request.getServletPath();
			if(path.contains("login")) {		// login 폴더 내에서 호출할 때 
				response.sendRedirect("login.jsp");
			}else {
				response.sendRedirect("/hotel_prj/login/login.jsp");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("성공");
		//		String url = "main.jsp";
//		HttpSession session = request.getSession();
//		if(session.getAttribute("u_idKey") != null) {
//			url = "user/userInfo.jsp";
//		}
//		RequestDispatcher rd = request.getRequestDispatcher(url);
//		rd.forward(request, response);
	}

}
