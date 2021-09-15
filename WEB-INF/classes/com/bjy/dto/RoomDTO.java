package com.bjy.dto;

/** 
create table room_tbl (room_id number(3) primary key,
room_type varchar2(50) not null,
room_size number(4) not null,
room_capacity number(3) not null,
room_price number(10) not null,
room_img varchar2(60),
room_count number(4) not null);

insert into room_tbl values (1, '스탠다드', 36, 2, 200000, 'data',3);
insert into room_tbl values (2, '디럭스', 62, 4, 250000, 'data',3);
insert into room_tbl values (3, '스위트', 102, 8, 400000, 'data',3);
insert into room_tbl values (4, '로열', 200, 12, 600000, 'data',3);
 * **/

/*DB에서 방 고유번호는 기본키로 설정*/
public class RoomDTO {
	private int room_id;			// 각 방에 대한 고유번호 (쉽게 불러오려고)
	private String room_type;		// 방 이름
	private int room_size;			// 방 크기
	private int room_capacity;		// 방 수용인원
	private int room_price;			// 방 가격
	private String room_img;		// 방 이미지 경로
	private int room_count;			// 방 갯수
	
	public RoomDTO() { }
	
	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_name) {
		this.room_type = room_name;
	}

	public int getRoom_size() {
		return room_size;
	}

	public void setRoom_size(int room_size) {
		this.room_size = room_size;
	}

	public int getRoom_capacity() {
		return room_capacity;
	}

	public void setRoom_capacity(int room_capacity) {
		this.room_capacity = room_capacity;
	}

	public int getRoom_price() {
		return room_price;
	}

	public void setRoom_price(int room_price) {
		this.room_price = room_price;
	}

	public String getRoom_img() {
		return room_img;
	}

	public void setRoom_img(String room_img) {
		this.room_img = room_img;
	}
	
	

	public int getRoom_count() {
		return room_count;
	}

	public void setRoom_count(int room_count) {
		this.room_count = room_count;
	}

	public void roomToString() {
		System.out.println(this.room_id + ", " + this.room_type + ", " + this.room_capacity + ", " + this.room_size + ", " + this.room_price + ", " + this.room_img);
	}
	
}
