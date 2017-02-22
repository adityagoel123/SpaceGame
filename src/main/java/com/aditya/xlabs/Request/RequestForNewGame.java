package com.aditya.xlabs.Request;

import java.io.Serializable;

import com.aditya.xlabs.APIType.ProtocolAPI;

public class RequestForNewGame implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2625953253221696805L;
	
	private String user_id;
	private String full_name;
	private String rules;
	private ProtocolAPI spaceship_protocol;
	
	
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public ProtocolAPI getSpaceship_protocol() {
		return spaceship_protocol;
	}
	public void setSpaceship_protocol(ProtocolAPI spaceship_protocol) {
		this.spaceship_protocol = spaceship_protocol;
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
	
	
}
