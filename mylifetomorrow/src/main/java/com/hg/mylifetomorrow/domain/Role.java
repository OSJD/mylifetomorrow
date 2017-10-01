package com.hg.mylifetomorrow.domain;

public enum Role {
	ROLE_USER("User"),
	ROLE_ADMIN("Admin"),
	ROLE_SITE_MNG("Manager");
	
	Role(String name){
		this.roleName=name;
	}

	private String roleName;
	
	public String getRoleName(){
		return this.roleName;
	}
}
