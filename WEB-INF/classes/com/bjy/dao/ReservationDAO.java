package com.bjy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.bjy.dto.ReservationDTO;
import com.bjy.dto.RoomDTO;

public class ReservationDAO {
	private DBConnectionDAO pool;
	
	public ReservationDAO() {
		pool = DBConnectionDAO.getInstance();
	}

	/** 투숙객 인원수를 매개변수로 받고 객실 수용 인원수와 비교하여 예약 가능한 방의 목록(배열)을 반환하는 메서드 **/
	public ArrayList<RoomDTO> selectRoomByPeople(int people) {
		ArrayList<RoomDTO> result = null;
		RoomDTO dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			result = new ArrayList<RoomDTO>();
	
			con = pool.getConnection();
			sql = "select * from room_tbl where room_capacity >=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, people);
			rs = pstmt.executeQuery();
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
			System.err.println("ReservationDAO - selectRoomByPeople() ERR : "+ e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
		return result;
	}
	
	/** 인원수에 따른 가능한 객실 타입, 체크인날짜, 체크아웃 날짜를 매개변수로 받아 예약 가능한 방을 찾아서 객실 타입의 배열을 반환하는 메서도**/
	public ArrayList<RoomDTO> selectRoomByDate(ArrayList<RoomDTO> result,String r_checkin,String r_checkout){
		ArrayList<RoomDTO> room = result;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int num = 0;
		
		for(int i=0;i<result.size();i++) {
			try {
				num = result.get(i).getRoom_count();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date a = sdf.parse(r_checkin);
				Date b = sdf.parse(r_checkout);
					
				con = pool.getConnection();
				sql = "select R_CHECKIN,R_CHECKOUT from reservation_tbl where R_TYPE=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, result.get(i).getRoom_type());
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					if(!(b.before(rs.getDate("r_checkin")) || a.after(rs.getDate("r_checkout")))) {
						num--;
					}
				}	
				
				if(num < 1) {
					room.remove(i);
				}
			}catch(Exception e) {
				System.err.println("ReservationDAO - selectRoomByDate() ERR : "+ e.getMessage());
			}finally {
				pool.freeConnection(pstmt, rs, con);
			}
		}
		return room;
	}
	
	/** 객실타입을 매개변수로 받고 해당하는 방의 정보를 저장하여 배열로 반환하는 메서드**/
	public RoomDTO[] selctRoomByType(ArrayList<String> room){
		RoomDTO[] result = new RoomDTO[room.size()];
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int i=0;
		for(String r : room) {
			try {
				con = pool.getConnection();
				sql = "select room_type, room_size, room_capacity,room_price,room_img from room_tbl where room_type=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, r);				
				rs = pstmt.executeQuery();
				rs.next();
				
				RoomDTO t = new RoomDTO();
				t.setRoom_type(rs.getString("room_type"));
				t.setRoom_size(rs.getInt("room_size"));
				t.setRoom_capacity(rs.getInt("room_capacity"));
				t.setRoom_price(rs.getInt("room_price"));
				t.setRoom_img(rs.getString("room_img"));
				
				result[i] = t;
				i++;
			}catch(Exception e) {
				System.err.println("ReservationDAO - selectRoomByPeople() ERR : "+ e.getMessage());
			}finally {
				pool.freeConnection(pstmt, rs, con);
			}
		}
		
		
		return result;
	}
	
	/** 예약할 방의 정보를 매개변수로 받아서 db reservation_tbl에 예약 데이터를 저장하는 메서드**/
	public int insertRoom(ReservationDTO r) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

			try {
				con = pool.getConnection();
				sql = "insert into reservation_tbl values (reservation_seq.nextval,?,?,?,TO_DATE(?,'YYYY-MM-DD'),TO_DATE(?,'YYYY-MM-DD'),?,?,sysdate)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, r.getR_id());				
				pstmt.setInt(2, r.getR_adults());				
				pstmt.setInt(3, r.getR_kids());				
				pstmt.setString(4, r.getR_checkin());				
				pstmt.setString(5, r.getR_checkout());				
				pstmt.setString(6, r.getR_type());				
				pstmt.setInt(7, r.getR_price());				

				result = pstmt.executeUpdate();
			}catch(Exception e) {
				System.err.println("ReservationDAO - insertRoom() ERR : "+ e.getMessage());
			}finally {
				pool.freeConnection(pstmt, rs, con);
			}
		
		return result;
	}
	
	/** 해당 회원의 예약 내역을 ArrayList<ReservationDTO> 배열에 저장해서 반환하는 메서드 **/
	public ArrayList<ReservationDTO> selectReservation(String u_idKey) {
		ArrayList<ReservationDTO> arlist = new ArrayList<ReservationDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

			try {
				con = pool.getConnection();
				sql = "select r_number,r_adults,r_kids,to_char(r_checkin,'YYYY-MM-DD DAY') r_checkin, to_char(r_checkout,'YYYY-MM-DD DAY') r_checkout,r_type,r_price,to_char(r_time,'YYYY-MM-DD DAY') r_time from reservation_tbl where r_id=? order by r_checkin";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, u_idKey);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					ReservationDTO dto = new ReservationDTO();
					dto.setR_number(rs.getInt("r_number"));
					dto.setR_adults(rs.getInt("r_adults"));
					dto.setR_kids(rs.getInt("r_kids"));
					dto.setR_checkin(rs.getString("r_checkin"));
					dto.setR_checkout(rs.getString("r_checkout"));
					dto.setR_type(rs.getString("r_type"));
					dto.setR_price(rs.getInt("r_price"));
					dto.setR_time(rs.getString("r_time"));
					
					arlist.add(dto);
				}
			}catch(Exception e) {
				System.err.println("ReservationDAO - selectReservation() ERR : "+ e.getMessage());
			}finally {
				pool.freeConnection(pstmt, rs, con);
			}
		
		return arlist;
	}
	
	/** 예약 내역의 번호를 매개변수로 받아서 해당 예약 내역을 db에서 삭제하는 메서드 **/
	public int cancelReservation(int[] r_numbers) {
		int num = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "delete from reservation_tbl where r_number=?";
			pstmt = con.prepareStatement(sql);
			for(int i=0;i<r_numbers.length;i++) {
				pstmt.setInt(1, r_numbers[i]);
				num += pstmt.executeUpdate();
			}

		}catch(Exception e) {
			System.err.println("ReservationDAO - selectReservation() ERR : "+ e.getMessage());
		}finally {
			pool.freeConnection(pstmt, con);
		}
		return num;
	}
	
	/** --------------------- 관리자 화면 전용 메서드 ----------------------- **/
	/** 모든 예약 정보를 반환하는 메서드**/
	public ArrayList<ReservationDTO> selectReserve(){
		ArrayList<ReservationDTO> result = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			result = new ArrayList<ReservationDTO>();
			con = pool.getConnection();
			sql = "select r_number,r_id,r_adults,r_kids,to_char(r_checkin,'YYYY-MM-DD DAY') r_checkin, to_char(r_checkout,'YYYY-MM-DD DAY') r_checkout,r_type,r_price,to_char(r_time,'YYYY-MM-DD DAY') r_time from reservation_tbl order by r_number";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				ReservationDTO dto = new ReservationDTO();
				dto.setR_number(rs.getInt("r_number"));
				dto.setR_id(rs.getString("r_id"));
				dto.setR_checkin(rs.getString("r_checkin"));
				dto.setR_checkout(rs.getString("r_checkout"));
				dto.setR_adults(rs.getInt("r_adults"));
				dto.setR_kids(rs.getInt("r_kids"));
				dto.setR_type(rs.getString("r_type"));
				dto.setR_price(rs.getInt("r_price"));
				dto.setR_time(rs.getString("r_time"));
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
