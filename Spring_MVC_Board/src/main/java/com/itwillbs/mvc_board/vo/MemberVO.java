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
	private String email1; /*23/01/17 추가*/
	private String email2;
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
		
		//---------------------------- 23/01/18 추가
		// 전체 이메일을 결합된 상태로 email 멤버변수에 저장 시
		// "@" 기호 기준 문자열 분리를 통해 email1, email2에 각각 저장 작업 추가
		
//		email = email1 + "@" + email2;
//		String[] arrEmail = email.split("@");
		email1 = email.split("@")[0];
		email2 = email.split("@")[1];
		
	}
	/*23/01/17 추가------------------------*/
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
		
		// 1. email 파라미터로 결합하는 과정을 자동으로 처리하기 위해
		// email2 저장 시 email1 + email2 값도 함께 email 변수에 저장
		// 계정+도메인 = email에 넣어주기
//		email = email1 + "@" + email2;
		
	}
	/*23/01/17 추가------------------------*/
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
		return "MemberVO [name=" + name + ", id=" + id + ", passwd=" + passwd + ", email=" + email + ", email1="
				+ email1 + ", email2=" + email2 + ", gender=" + gender + ", date=" + date + ", auth_status="
				+ auth_status + "]";
	}
	
	
	
	
}
