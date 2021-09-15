package com.bjy.dto;

/** 
create table user_tbl (u_name varchar2(20) not null,
u_id varchar2(30) primary key,
u_pwd varchar2(40) not null,
u_phone varchar2(15) not null,
u_email varchar2(50) not null,
u_admin varchar2(2) default 'n' not null);

insert into user_tbl values ('관리자','admin','1234','01012345678','overtonhotel@overtonhotel.com', 'y');

**/

/* 회원가입한 고객들의 정보 저장하는 DTO */
public class UserDTO {
	private String u_name;		// 고객명
	private String u_id;		// 고객 ID
	private String u_pwd;		// 고객 PWD
	private String u_phone;		// 고객 전화번호
	private String u_email;		// 고객 이메일
	
	public UserDTO() {
		
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getU_pwd() {
		return u_pwd;
	}

	public void setU_pwd(String u_pwd) {
		this.u_pwd = u_pwd;
	}

	public String getU_phone() {
		return u_phone;
	}

	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}

	public String getU_email() {
		return u_email;
	}

	public void setU_email(String u_email) {
		this.u_email = u_email;
	}

	
	public void uToString() {
		System.out.println(this.u_name + ", " + this.u_id + ", " + this.u_pwd + ", " + this.u_phone + ", " + this.u_email);
	}

}
