package com.hg.mylifetomorrow.domain;

import java.io.Serializable;

public class EmailData implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String to;
	private String userName;
	private String subject;
	private String bodyQ;
	private String bodyR;
	private String bodyTemplate;
	private String quesOrReply;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getBodyTemplate() {
		return bodyTemplate;
	}
	public void setBodyTemplate(String bodyTemplate) {
		this.bodyTemplate = bodyTemplate;
	}
	public String getQuesOrReply() {
		return quesOrReply;
	}
	public void setQuesOrReply(String quesOrReply) {
		this.quesOrReply = quesOrReply;
	}
	public String getBodyQ() {
		return bodyQ;
	}
	public void setBodyQ(String bodyQ) {
		this.bodyQ = bodyQ;
	}
	public String getBodyR() {
		return bodyR;
	}
	public void setBodyR(String bodyR) {
		this.bodyR = bodyR;
	}

}
