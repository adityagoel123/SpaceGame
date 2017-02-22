package com.aditya.xlabs.Response;

import java.io.Serializable;


public class ResponseForNewGame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3934718571651959018L;
	
	private String user_id;
	private String full_name;
	private String game_id; // "game_id": "match-1",
	private String starting; // Populate Requester Player's User ID. "starting": "xebialabs-1"
	private String rules;
	
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getGame_id() {
		return game_id;
	}
	public void setGame_id(String game_id) {
		this.game_id = game_id;
	}
	public String getStarting() {
		return starting;
	}
	public void setStarting(String starting) {
		this.starting = starting;
	}
	
	
}	
