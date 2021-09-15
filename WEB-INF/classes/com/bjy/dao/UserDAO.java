package com.bjy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.bjy.dto.ReservationDTO;
import com.bjy.dto.UserDTO;

public class UserDAO {
	private DBConnectionDAO pool;
	
	public UserDAO() {
		pool = DBConnectionDAO.getInstance();
	}
	
	// ID 중복확인 메서드 ( 중복된 아이디 - true / 신규 아이디 - false )
	public boolean checkId(String u_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		
		try {
			con = pool.getConnection();
			sql = "select u_id from user_tbl where u_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			rs = pstmt.executeQuery();
			flag = rs.next();
		}catch(Exception e) {
			System.err.println("UserDAO - checkId() ERR : "+ e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
		return flag;
	}
	
	/** 회원가입 정보를 지닌 dto 객체를 받아서 user_tbl의 데이터로 저장해주는 메서드**/
	// DB 데이터 저장 완료시 true, 저장되지 않으면 false 반환
	public boolean insertUser(UserDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		
		try {
			con = pool.getConnection();
			sql = "insert into user_tbl values (?,?,?,?,?,default)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getU_name());
			pstmt.setString(2, dto.getU_id());
			pstmt.setString(3, dto.getU_pwd());
			pstmt.setString(4, dto.getU_phone());
			pstmt.setString(5, dto.getU_email());
			
			if(pstmt.executeUpdate() == 1) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			pool.freeConnection(pstmt, con);
		}
		return flag;
	}
	
	/** 로그인 확인하는 메서드 **/
	public int loginUser(String u_id,String u_pwd) {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		String sql= null;
		int mode= 0;
		
		// 0-id false, 1-id true & pwd false, 2-id&pwd true
		try {
			if(!checkId(u_id))
				return mode;
			con = pool.getConnection();
			sql = "select u_id, u_pwd from user_tbl where u_id=? and u_pwd=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			pstmt.setString(2, u_pwd);
			rs = pstmt.executeQuery();
			if(rs.next())
				mode = 2;
			else
				mode = 1;
		}catch(Exception e) {
			System.err.println("UserDAO - loginUser() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
		return mode;
	}
	
	/** 관리자 로그인 확인하는 메서드 **/
	public int loginAdmin(String u_id,String u_pwd) {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		String sql= null;
		int mode= 0;
		
		// 0-false , 1-true
		try {
			con = pool.getConnection();
			sql = "select u_id, u_pwd from user_tbl where u_id=? and u_pwd=? and u_admin='y'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			pstmt.setString(2, u_pwd);
			rs = pstmt.executeQuery();
			if(rs.next())
				mode = 1;
		}catch(Exception e) {
			System.err.println("UserDAO - loginAdmin() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
		return mode;
	}
	
	/** 해당 아이디를 가진 고객의 정보를 db에서 가져와 전달하는 메서드**/
	public UserDTO selectUser(String u_idKey) {
		UserDTO dto = new UserDTO();
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		String sql= null;
		
		try {
			con = pool.getConnection();
			sql = "select u_name, u_id, u_pwd,u_phone,u_email from user_tbl where u_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_idKey);
			rs = pstmt.executeQuery();
			
			rs.next();
			dto.setU_name(rs.getString(1));
			dto.setU_id(rs.getString(2));
			dto.setU_pwd(rs.getString(3));
			dto.setU_phone(rs.getString(4));
			dto.setU_email(rs.getString(5));
		}catch(Exception e) {
			System.err.println("UserDAO - selectUser() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
		return dto;
	}

	/** 수정할 정보를 받아서 db의 user_tbl에서 고객 정보를 수정하는 메소드 **/
	public int updateUser(UserDTO udto) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt= null;
		String sql= null;
		
		try {
			con = pool.getConnection();
			sql = "update user_tbl set u_name=?,u_pwd=?,u_phone=?,u_email=? where u_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, udto.getU_name());
			pstmt.setString(2, udto.getU_pwd());
			pstmt.setString(3, udto.getU_phone());
			pstmt.setString(4, udto.getU_email());
			pstmt.setString(5, udto.getU_id());
			
			result = pstmt.executeUpdate();
		}catch(Exception e) {
			System.err.println("UserDAO - selectUser() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, con);
		}
		return result;
	}
	
	/** ----------------- 관리자 전용 메서드 --------------------- **/
	/** 모든 고객의 정보를 반환하는 메서드 **/
	public ArrayList<UserDTO> selectUser(){
		ArrayList<UserDTO> result = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			result = new ArrayList<UserDTO>();
			con = pool.getConnection();
			sql = "select u_name, u_id, u_pwd, u_phone, u_email from user_tbl where u_admin='n'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				UserDTO dto = new UserDTO();
				dto.setU_name(rs.getString("u_name"));
				dto.setU_id(rs.getString("u_id"));
				dto.setU_pwd(rs.getString("u_pwd"));
				dto.setU_phone(rs.getString("u_phone"));
				dto.setU_email(rs.getString("u_email"));
				result.add(dto);
			}
			
		}catch(Exception e) {
			System.err.println("ReservationDAO - selectReserve() ERR : "+ e.getMessage());
		}finally {
			pool.freeConnection(stmt, rs, con);
		}
		return result;
	}
}
