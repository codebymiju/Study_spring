package com.itwillbs.test.vo;

import java.sql.Date;

/*
 * member 테이블 정의
 * -------------------------
 * 이름(name) - VARCHAR(20) NN
 * 아이디(id) - VARCHAR(16) PK
 * 패스워드(passwd) - VARCHAR(16) NN
 * E-Mail(email) - VARCHAR(50), UN, NN
 * 성별(gender) - VARCHAR(1) NN
 * 가입일(date) - DATE NN
 * -----------------------------------
  CREATE TABLE member (
  		name VARCHAR(20) NOT NULL,
  		id VARCHAR(16) PRIMARY KEY,
  		passwd VARCHAR(16) NOT NULL,
  		email VARCHAR(50) UNIQUE NOT NULL,
  		gender VARCHAR(1) NOT NULL,
  		date DATE NOT NULL
  );
 */
public class MemberVO {
	private String name;
	private String id;
	private String passwd;
	private String email;
	private String gender;
	private Date date; // java.sql 패키지
	
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
	@Override
	public String toString() {
		return "MemberBean [name=" + name + ", id=" + id + ", passwd=" + passwd + ", email=" + email + ", gender="
				+ gender + ", date=" + date + "]";
	}
	
	
}














