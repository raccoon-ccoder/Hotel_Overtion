package com.bjy.dto;

/** 
create table reservation_tbl (r_number number(4) primary key,
r_id varchar2(30) not null,
r_adults number(3) not null,
r_kids number(3) not null,
r_checkin date not null,
r_checkout date not null,
r_type varchar2(50) not null,
r_price number(10) not null,
r_time date default sysdate);


create sequence reservation_seq
start with 1
increment by 1;
 * **/

/* 예약 정보 관련 클래스 */
public class ReservationDTO {
	private int r_number;		// 예약 번호 
	private String r_id;		// 예약 고객 ID
	private int r_adults;		// 투숙 성인인원 수
	private int r_kids;			// 투숙  아동인원 수
	private String r_checkin;	// 체크인 날짜
	private String r_checkout;	// 체크아웃 날짜
	private String r_type;		// 예약한 방 종류
	private int r_price;		// 총 금액
	private String r_time;		// 예약한 시간
	
	public ReservationDTO() {
		
	}

	public int getR_number() {
		return r_number;
	}

	public void setR_number(int r_number) {
		this.r_number = r_number;
	}

	public String getR_id() {
		return r_id;
	}

	public void setR_id(String r_id) {
		this.r_id = r_id;
	}

	public int getR_adults() {
		return r_adults;
	}

	public void setR_adults(int r_adults) {
		this.r_adults = r_adults;
	}

	public int getR_kids() {
		return r_kids;
	}

	public void setR_kids(int r_kids) {
		this.r_kids = r_kids;
	}

	public String getR_checkin() {
		return r_checkin;
	}

	public void setR_checkin(String r_checkin) {
		this.r_checkin = r_checkin;
	}

	public String getR_checkout() {
		return r_checkout;
	}

	public void setR_checkout(String r_checkout) {
		this.r_checkout = r_checkout;
	}

	public String getR_type() {
		return r_type;
	}

	public void setR_type(String r_name) {
		this.r_type = r_name;
	}

	public int getR_price() {
		return r_price;
	}

	public void setR_price(int r_price) {
		this.r_price = r_price;
	}

	public String getR_time() {
		return r_time;
	}

	public void setR_time(String r_time) {
		this.r_time = r_time;
	}
	
	public void rToString() {
		System.out.println(this.r_number +  ", " + this.r_id + ", " + this.r_adults + ", " + this.r_kids + ", " + this.r_checkin + ", " + this.r_checkout + ", " + this.r_type + ", " + this.r_price + ", " + this.r_time);
	}

}
