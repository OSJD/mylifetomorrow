package com.hg.mylifetomorrow.domain;

import java.io.Serializable;

public class Question implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int questionId;
	private int profileId;
	private String question;
	private String datePosted;
	private boolean replied=false;
	private String postedBy;
	private String shortName;
	private String color;
	private boolean noQuestions;
	
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}

	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	public boolean isNoQuestions() {
		return noQuestions;
	}
	public void setNoQuestions(boolean noQuestions) {
		this.noQuestions = noQuestions;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public boolean isReplied() {
		return replied;
	}
	public void setReplied(boolean replied) {
		this.replied = replied;
	}

}
