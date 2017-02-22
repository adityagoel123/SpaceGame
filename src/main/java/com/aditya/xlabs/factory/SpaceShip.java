package com.aditya.xlabs.factory;

import com.aditya.xlabs.enums.SpaceShipTypeEnum;

public abstract class SpaceShip {
	
	public SpaceShip(SpaceShipTypeEnum model) {
		this.model = model;
	}
    
	private int shotsThisShipCanBear;
	
	private SpaceShipTypeEnum model = null;
    
	public SpaceShipTypeEnum getModel() {
		return model;
	}

	public void setModel(SpaceShipTypeEnum model) {
		this.model = model;
	}

	public int getShotsThisShipCanBear() {
		return shotsThisShipCanBear;
	}

	public void setShotsThisShipCanBear(int shotsThisShipCanBear) {
		this.shotsThisShipCanBear = shotsThisShipCanBear;
	}

	// Designed for any subclass level processing in this method from future prospective;
	protected abstract void addToSpaceShip();

}

