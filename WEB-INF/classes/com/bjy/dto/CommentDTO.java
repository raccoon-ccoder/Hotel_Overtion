package com.bjy.dto;

/** 
create table comment_tbl (
b_number number(3) not null, 
c_number number(3) not null, 
c_writer varchar2(30) not null, 
c_comment varchar2(1000) not null, 
c_time date default sysdate) not null; 

create sequence comment_seq
start with 1
increment by 1;
 * 
 * **/

/* 게시글에 달린 댓글 관련 클래스 */
public class CommentDTO {
	private int b_number;		// 해당 댓글이 있는 게시글의 번호
	private int c_number;		// 댓글 번호
	private String c_writer;	// 댓글을 쓴 작성자
	private String c_comment;	// 댓글 내용
	private String c_time;		// 댓글 작성한 시간 
	
	public int getB_number() {
		return b_number;
	}

	public void setB_number(int b_number) {
		this.b_number = b_number;
	}

	public int getC_number() {
		return c_number;
	}

	public void setC_number(int c_number) {
		this.c_number = c_number;
	}

	public String getC_writer() {
		return c_writer;
	}

	public void setC_writer(String c_writer) {
		this.c_writer = c_writer;
	}

	public String getC_comment() {
		return c_comment;
	}

	public void setC_comment(String c_comment) {
		this.c_comment = c_comment;
	}

	public String getC_time() {
		return c_time;
	}

	public void setC_time(String c_time) {
		this.c_time = c_time;
	}

	public void cToString() {
		System.out.println(this.c_number + ", " + this.c_writer + ", " + this.c_comment + ", "  + this.c_time);
	}

}
