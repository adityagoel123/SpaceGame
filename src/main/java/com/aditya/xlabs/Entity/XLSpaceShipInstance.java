package com.aditya.xlabs.Entity;

import java.util.HashMap;
import java.util.Map;

import com.aditya.xlabs.factory.SpaceShip;

public class XLSpaceShipInstance {
	
	private Player ownerOfInstance;
	private Map<String,SpaceShip> posnToShipsMap = new HashMap<String,SpaceShip>();
	private boolean nextTurnBelongsToThisInstance;
	private int eligibleCountOfShotsForNextTurn =0;
	private String ipAddressPlayingFrom;
	private String portPlayingFrom;
	
	
	public String getIpAddressPlayingFrom() {
		return ipAddressPlayingFrom;
	}

	public void setIpAddressPlayingFrom(String ipAddressPlayingFrom) {
		this.ipAddressPlayingFrom = ipAddressPlayingFrom;
	}

	public String getPortPlayingFrom() {
		return portPlayingFrom;
	}

	public void setPortPlayingFrom(String portPlayingFrom) {
		this.portPlayingFrom = portPlayingFrom;
	}

	public int getEligibleCountOfShotsForNextTurn() {
		return eligibleCountOfShotsForNextTurn;
	}

	public void setEligibleCountOfShotsForNextTurn(
			int eligibleCountOfShotsForNextTurn) {
		this.eligibleCountOfShotsForNextTurn = eligibleCountOfShotsForNextTurn;
	}

	public Map<String, SpaceShip> getPosnToShipsMap() {
		return posnToShipsMap;
	}

	public void setPosnToShipsMap(Map<String, SpaceShip> posnToShipsMap) {
		this.posnToShipsMap = posnToShipsMap;
	}

	public Player getOwnerOfInstance() {
		return ownerOfInstance;
	}

	public void setOwnerOfInstance(Player ownerOfInstance) {
		this.ownerOfInstance = ownerOfInstance;
	}

	public boolean isNextTurnBelongsToThisInstance() {
		return nextTurnBelongsToThisInstance;
	}

	public void setNextTurnBelongsToThisInstance(
			boolean nextTurnBelongsToThisInstance) {
		this.nextTurnBelongsToThisInstance = nextTurnBelongsToThisInstance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ownerOfInstance == null) ? 0 : ownerOfInstance.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XLSpaceShipInstance other = (XLSpaceShipInstance) obj;
		if (ownerOfInstance == null) {
			if (other.ownerOfInstance != null)
				return false;
		} else if (!ownerOfInstance.equals(other.ownerOfInstance))
			return false;
		return true;
	}
		
}
