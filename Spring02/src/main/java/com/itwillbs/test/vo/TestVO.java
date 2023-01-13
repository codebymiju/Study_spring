package com.itwillbs.test.vo;

// XXXVO = XXXDTO = XXXBean
public class TestVO {

	private String subject;
	private String content;
	
	
	public TestVO() {}

	public TestVO(String subject, String content) {
		super();
		this.subject = subject;
		this.content = content;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
