package com.aditya.xlabs.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
	
	private String gameID;
	private Date startTime;
	private Date endTime;
	private String rules;
	private List<XLSpaceShipInstance> currenlyPlayingInstances = new ArrayList<XLSpaceShipInstance>();
	
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	public List<XLSpaceShipInstance> getCurrenlyPlayingInstances() {
		return currenlyPlayingInstances;
	}
	public void setCurrenlyPlayingInstances(
			List<XLSpaceShipInstance> currenlyPlayingInstances) {
		this.currenlyPlayingInstances = currenlyPlayingInstances;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getGameID() {
		return gameID;
	}
	
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
		
}
