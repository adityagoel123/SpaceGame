package com.aditya.xlabs.Request;

import java.io.Serializable;
import java.util.List;

public class RequestForFiringSalvoOfShots implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2625953253221696805L;
	
	private List<String> salvo;

	public List<String> getSalvo() {
		return salvo;
	}

	public void setSalvo(List<String> salvo) {
		this.salvo = salvo;
	}

	public RequestForFiringSalvoOfShots(List<String> salvo) {
		super();
		this.salvo = salvo;
	}

	public RequestForFiringSalvoOfShots() {
		super();
	}

	
	
	
	
}
