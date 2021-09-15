package com.bjy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.bjy.dto.RoomDTO;

public class RoomDAO {
	private DBConnectionDAO pool;
	
	public RoomDAO() {
		pool = DBConnectionDAO.getInstance();
	}
	
	/** 관리자 전용 메서드 **/
	/** 모든 객실 타입 정보를 저장하여 반환하는 메서드 **/
	public ArrayList<RoomDTO> selectRoom() {
		ArrayList<RoomDTO> result = null;
		RoomDTO dto = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			result = new ArrayList<RoomDTO>();
			con = pool.getConnection();
			sql = "select * from room_tbl order by room_id";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				dto = new RoomDTO();
				dto.setRoom_id(rs.getInt("room_id"));
				dto.setRoom_type(rs.getString("room_type"));
				dto.setRoom_size(rs.getInt("room_size"));
				dto.setRoom_capacity(rs.getInt("room_capacity"));
				dto.setRoom_price(rs.getInt("room_price"));
				dto.setRoom_img(rs.getString("room_img"));
				dto.setRoom_count(rs.getInt("room_count"));
				result.add(dto);
			}
		}catch(Exception e) {
			System.err.println("RoomDAO - selectRoom() ERR : "+ e.getMessage());
		}finally {
			pool.freeConnection(stmt, rs, con);
		}
		return result;
	}
	
	/** 객실 타입을 매개변수로 받아 해당하는 객실의 모든 정보를 반환하는 메서드 **/
	public RoomDTO selectRoomType(String room_type) {
		RoomDTO dto = new RoomDTO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "select * from room_tbl where room_type=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, room_type);
			rs = pstmt.executeQuery();
			
			rs.next();
			dto.setRoom_id(rs.getInt("room_id"));
			dto.setRoom_type(rs.getString("room_type"));
			dto.setRoom_size(rs.getInt("room_size"));
			dto.setRoom_capacity(rs.getInt("room_capacity"));
			dto.setRoom_price(rs.getInt("room_price"));
			dto.setRoom_img(rs.getString("room_img"));
			dto.setRoom_count(rs.getInt("room_count"));
		}catch(Exception e) {
			System.err.println("RoomDAO - selectRoom() ERR : "+ e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
		return dto;
	}
	
	/** 수정할 정보가 담긴 RoomDTO 객체를 매개변수로 받아 db에 수정사항을 반영하는 메서드 **/
	public void updateRoom(RoomDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "update room_tbl set room_type=?,room_size=?,room_capacity=?,room_price=?,room_count=? where room_id=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getRoom_type());
			pstmt.setInt(2, dto.getRoom_size());
			pstmt.setInt(3, dto.getRoom_capacity());
			pstmt.setInt(4, dto.getRoom_price());
			pstmt.setInt(5, dto.getRoom_count());
			pstmt.setInt(6, dto.getRoom_id());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			System.err.println("RoomDAO - updateRoom() ERR : "+ e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
	}
		
}
