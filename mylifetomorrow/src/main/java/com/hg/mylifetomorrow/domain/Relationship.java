package com.hg.mylifetomorrow.domain;

public enum Relationship {
	Subscriber(1),
	Spouse(2),
	Son(3),
	Daughter(4),
	Father(5),
	Mother(6),
	Brother(7),
	Sister(8),
	Friend(9);
	
	Relationship(int code){
		this.code=code;
	}

	private int code;
	
	public int getCode(){
		return this.code;
	}
	
	public static String getRelationshipName(int code){
		for(Relationship relationship : values()){
			if(relationship.getCode()==code){
				return relationship.name();
			}
		}
		return "";
	}

}
