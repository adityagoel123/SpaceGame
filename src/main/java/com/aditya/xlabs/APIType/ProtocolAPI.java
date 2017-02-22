package com.aditya.xlabs.APIType;

import java.io.Serializable;

public class ProtocolAPI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String hostname;  //"hostname": "127.0.0.1",
	private String port; // "port": 9001
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	

}
