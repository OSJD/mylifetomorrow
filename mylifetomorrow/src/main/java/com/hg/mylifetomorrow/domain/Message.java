package com.hg.mylifetomorrow.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Message implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean success;
	private String message;
	private List<Profile> profiles;	
	private Profile currentProfile;
	private User user;
	private Map<Integer,List<Question>> questions;
	private List<Question> pendingQuestions;
	private Question currentQuestion;
	private List<Question> chatConversation;


	public Message(){
		
	}
	
	public Message(boolean success,String message){
		this.success=success;
		this.message=message;
	}
	
	public Message(boolean success){
		this.success=success;
	}
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public Profile getCurrentProfile() {
		return currentProfile;
	}

	public void setCurrentProfile(Profile currentProfile) {
		this.currentProfile = currentProfile;
	}

	public Map<Integer, List<Question>> getQuestions() {
		return questions;
	}

	public void setQuestions(Map<Integer, List<Question>> questions) {
		this.questions = questions;
	}

	public List<Question> getPendingQuestions() {
		return pendingQuestions;
	}

	public void setPendingQuestions(List<Question> pendingQuestions) {
		this.pendingQuestions = pendingQuestions;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public List<Question> getChatConversation() {
		return chatConversation;
	}

	public void setChatConversation(List<Question> chatConversation) {
		this.chatConversation = chatConversation;
	}

		
}
