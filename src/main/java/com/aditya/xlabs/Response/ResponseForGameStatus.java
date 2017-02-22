package com.aditya.xlabs.Response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ResponseForGameStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3934718571651959018L;
	
	private Map<String, String> self = new HashMap<String, String>();
	private Map<String, String> opponent = new HashMap<String, String>();
	private Map<String, String> game = new HashMap<String, String>();
	
	public Map<String, String> getSelf() {
		return self;
	}
	public void setSelf(Map<String, String> self) {
		this.self = self;
	}
	public Map<String, String> getOpponent() {
		return opponent;
	}
	public void setOpponent(Map<String, String> opponent) {
		this.opponent = opponent;
	}
	public Map<String, String> getGame() {
		return game;
	}
	public void setGame(Map<String, String> game) {
		this.game = game;
	}

	
}	
