package com.bjy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bjy.dto.BoardDTO;

public class BoardDAO {
	private DBConnectionDAO pool;
	
	public BoardDAO() {
		pool = DBConnectionDAO.getInstance();
	}
	
	/** 게시글 전체 db를 Arrayslist에 저장하여 반환하는 메소드**/
	public ArrayList<BoardDTO> getBoradList(){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<BoardDTO> alist = new ArrayList<>();
		
		try {
			con = pool.getConnection();
			sql = "select b_number, b_title, b_writer, b_time from board_tbl order by b_number desc";
			pstmt =  con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO b = new BoardDTO();
				b.setB_number(rs.getInt(1));
				b.setB_title(rs.getString(2));
				b.setB_writer(rs.getString(3));
				b.setB_time(rs.getDate(4) + "");
				alist.add(b);
			}
		}catch(Exception e) {
			System.err.println("BoardDAO - getBoradList() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
		return alist;
	}
	/** 게시글 상세 정보를 저장하여 반환하는 메서드**/
	public BoardDTO getBorad(int b_number){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		BoardDTO b = null;
		
		try {
			con = pool.getConnection();
			sql = "select b_number, b_title, b_writer, b_time, b_content from board_tbl where b_number=?";
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, b_number);
			rs = pstmt.executeQuery();
			rs.next();
			
			b = new BoardDTO();
			b.setB_number(rs.getInt(1));
			b.setB_title(rs.getString(2));
			b.setB_writer(rs.getString(3));
			b.setB_time(rs.getDate(4) + "");
			b.setB_content(rs.getString(5));
				
		}catch(Exception e) {
			System.err.println("BoardDAO - getBorad() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
		return b;
	}
	
	/** 작성한 글 정보를 매개변수로 받아서 board_tbl의 데이터로 저정하는 메소드 **/
	public void insertBoard(String b_title, String b_writer, String b_content) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sql = "insert into board_tbl(b_number,b_title,b_writer,b_content) values (board_seq.nextval, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, b_title);
			pstmt.setString(2, b_writer);
			pstmt.setString(3, b_content);
			result = pstmt.executeUpdate();
			
			if(result >0) {
				System.out.println("게시글 작성 완료");
			}else {
				System.out.println("게시글 작성 실패");
			}
		}catch(Exception e) {
			System.err.println("BoardDAO - insertBoard() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, con);
		}
	}
	
	/** 해당 게시글을 삭제하는 메소드**/
	public void deleteBoard(int b_number) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sql = "delete from board_tbl where b_number=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, b_number);
			result = pstmt.executeUpdate();
			
			if(result >0) {
				System.out.println("게시글 삭제 완료");
			}else {
				System.out.println("게시글 삭제 실패");
			}
		}catch(Exception e) {
			System.err.println("BoardDAO - deleteBoard() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, con);
		}
	}
	
	/** 원하는 게시글의 내용, 제목만 수정할 수 있는 메서도**/
	public void updateBoard(int b_number,String b_title, String b_content) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;
		
		try {
			con = pool.getConnection();
			sql = "update board_tbl set b_title=?, b_content=?, b_time=SYSDATE where b_number=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, b_title);
			pstmt.setString(2, b_content);
			pstmt.setInt(3, b_number);
			result = pstmt.executeUpdate();
			
			if(result ==1) {
				System.out.println("게시글 수정 성공");
			}else {
				System.out.println("게시글 수정 실패");
			}
		}catch(Exception e) {
			System.err.println("BoardDAO - UpdateBoard() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, con);
		}
	}

}
