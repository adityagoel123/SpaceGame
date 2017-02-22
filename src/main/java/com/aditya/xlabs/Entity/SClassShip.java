package com.aditya.xlabs.Entity;

import com.aditya.xlabs.enums.SpaceShipTypeEnum;
import com.aditya.xlabs.factory.SpaceShip;

public class SClassShip extends SpaceShip {

	public SClassShip(int countOfShotsThisShipCanBear) {
		super(SpaceShipTypeEnum.SCLASS);
		setShotsThisShipCanBear(countOfShotsThisShipCanBear);
	}

	@Override
	protected void addToSpaceShip() {
		System.out.println("Building S-Class Ship");
	}
	

}
