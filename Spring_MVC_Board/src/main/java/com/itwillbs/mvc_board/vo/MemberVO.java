package com.itwillbs.mvc_board.vo;

import java.sql.Date;

public class MemberVO {
/*
 *  CREATE TABLE member (
	name VARCHAR(20) NOT NULL,
	id VARCHAR(16) PRIMARY KEY,
	passwd VARCHAR(16) NOT NULL,
	email VARCHAR(50) UNIQUE NOT NULL,
	gender VARCHAR(1) NOT NULL,
	date DATE NOT NULL,
	auth_status CHAR(1) NOT NULL 인증여부 
	);
	----------------------------
	기존 테이블에 인증 상태 컬럼 추가
	ALTER TABLE member ADD auth_status CHAR(1) NOT NULL;
 */
	private String name;
	private String id;
	private String passwd;
	private String email;
	private String gender;
   	private Date date; // java.sql
   	private String auth_status; // 23/01/11 추가 
   	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAuth_status() {
		return auth_status;
	}
	public void setAuth_status(String auth_status) {
		this.auth_status = auth_status;
	}
	
	@Override
	public String toString() {
		return "MemberVO [name=" + name + ", id=" + id + ", passwd=" + passwd + ", email=" + email + ", gender="
				+ gender + ", date=" + date + ", auth_status=" + auth_status + "]";
	}
	
	
	
}
