package com.aditya.xlabs.Entity;

import com.aditya.xlabs.enums.SpaceShipTypeEnum;
import com.aditya.xlabs.factory.SpaceShip;

public class AClassShip extends SpaceShip {

	public AClassShip(int countOfShotsThisShipCanBear) {
		super(SpaceShipTypeEnum.ACLASS);
		setShotsThisShipCanBear(countOfShotsThisShipCanBear);
	}

	@Override
	protected void addToSpaceShip() {
		System.out.println("Building A-Class Ship");
	}

}
