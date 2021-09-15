package com.bjy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bjy.dto.BoardDTO;
import com.bjy.dto.CommentDTO;

public class CommentDAO {
	private DBConnectionDAO pool;
	
	public CommentDAO() {
		pool = DBConnectionDAO.getInstance();
	}
	
	/** inquiryList.jsp에서 특정 글 클릭시 글에 작성된 댓글들을 ArrayList에 저장하여 inquiryDetail.jsp에게 전달 **/
	public ArrayList<CommentDTO> selectComment(int b_number) {
		ArrayList<CommentDTO> result = new ArrayList<CommentDTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "select b_number,c_number,c_writer,c_comment,TO_CHAR(c_time,'YYYY-MM-DD') c_time from comment_tbl where b_number=? order by c_number";
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, b_number);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CommentDTO c = new CommentDTO();
				c.setB_number(rs.getInt("b_number"));
				c.setC_number(rs.getInt("c_number"));
				c.setC_writer(rs.getString("c_writer"));
				c.setC_comment(rs.getString("c_comment"));
				c.setC_time(rs.getString("c_time"));
				
				result.add(c);
			}
		}catch(Exception e) {
			System.err.println("CommnetDAO - selectComment() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, rs, con);
		}
		return result;
	}

	/** inquiryDetail.jsp에서 댓글 작성시 db에 저장해주는 메소드 **/
	public void insertComment(int b_number, String c_writer, String c_comment) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "insert into comment_tbl(b_number,c_number,c_writer,c_comment) values (?,comment_seq.nextval,?,?)";
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, b_number);
			pstmt.setString(2, c_writer);
			pstmt.setString(3, c_comment);
			int n = pstmt.executeUpdate();
			
			if(n>0) {
				System.out.println("댓글 작성 완료");
			}else {
				System.out.println("댓글 작성 실패");
			}
		}catch(Exception e) {
			System.err.println("CommentDAO - insertComment() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, con);
		}
	}
	
	/** inquiryDetail.jsp에서 댓글 삭제 버튼 클릭시 db의 data 삭제해주는 메소드**/
	public void deleteComment(int c_number) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "delete from comment_tbl where c_number=?";
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, c_number);
			int n = pstmt.executeUpdate();
			
			if(n>0) {
				System.out.println("댓글 삭제 완료");
			}else {
				System.out.println("댓글 삭제 실패");
			}
		}catch(Exception e) {
			System.err.println("CommentDAO - deleteComment() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, con);
		}
	}
	
	/** 댓글 번호와 수정된 댓글 내용을 매개변수로 받아서 comment_tbl db data 수정하고 결과를 반환하는 메소드 **/
	public void updateComment(int c_number, String c_comment) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "update comment_tbl set c_comment=?, c_time=sysdate where c_number=?";
			pstmt =  con.prepareStatement(sql);
			pstmt.setString(1, c_comment);
			pstmt.setInt(2, c_number);
			int n = pstmt.executeUpdate();
			
			if(n>0) {
				System.out.println("댓글 수정 완료");
			}else {
				System.out.println("댓글 수정 실패");
			}
		}catch(Exception e) {
			System.err.println("CommentDAO - updateComment() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, con);
		}
	}
	
	/** 게시글 삭제시 해당 게시글에 달린 모든 댓글들 삭제하는 메소드 **/
	public void deleteAllComment(int b_number) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			con = pool.getConnection();
			sql = "delete from comment_tbl where b_number=?";
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, b_number);
			int n = pstmt.executeUpdate();
			
			if(n>0) {
				System.out.println(b_number+ "번째 게시글 총 댓글 삭제 완료");
			}else {
				System.out.println(b_number+ "번째 게시글 총 댓글 삭제 실패");
			}
		}catch(Exception e) {
			System.err.println("CommentDAO - deleteComment() ERR : " + e.getMessage());
		}finally {
			pool.freeConnection(pstmt, con);
		}
	}
}
