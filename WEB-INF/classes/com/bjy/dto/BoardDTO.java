package com.bjy.dto;

/** 
create table board_tbl (b_number number(3) primary key,
b_title varchar2(60) not null,
b_writer varchar2(30) not null,
b_time date default sysdate not null,
b_content varchar2(3000) not null);

* create sequence board_seq 
  start with 1
  increment by 1;

**/
/* 고객의 소리(문의사항) 게시판 관련 클래스 */
public class BoardDTO {
	private int b_number; 		// 게시글 번호
	private String b_title;		// 게시글 제목
	private String b_writer;	// 게시글 작성자
	private String b_time;		// 게시글 시간
	private String b_content;	// 게시글 내용
	
	public BoardDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getB_number() {
		return b_number;
	}

	public void setB_number(int b_number) {
		this.b_number = b_number;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_writer() {
		return b_writer;
	}

	public void setB_writer(String b_writer) {
		this.b_writer = b_writer;
	}

	public String getB_time() {
		return b_time;
	}

	public void setB_time(String b_time) {
		this.b_time = b_time;
	}

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public void bToString() {
		System.out.println(this.b_number +  ", " + this.b_title + ", " + this.b_writer + ", "  + this.b_content + ", " + this.b_time);
	}
}
