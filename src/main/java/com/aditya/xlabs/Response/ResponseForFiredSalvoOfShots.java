package com.aditya.xlabs.Response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ResponseForFiredSalvoOfShots implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3934718571651959018L;
	
	// Map to Store the mapping between "Position shot At" AND "Status of Ship At that Position".
	private Map<String, String> salvo = new HashMap<String, String>();
	private Map<String, String> game = new HashMap<String, String>();

	public Map<String, String> getSalvo() {
		return salvo;
	}

	public void setSalvo(Map<String, String> salvo) {
		this.salvo = salvo;
	}

	public Map<String, String> getGame() {
		return game;
	}

	public void setGame(Map<String, String> game) {
		this.game = game;
	}
	
	
}	
