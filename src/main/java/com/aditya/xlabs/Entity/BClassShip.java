package com.aditya.xlabs.Entity;

import com.aditya.xlabs.enums.SpaceShipTypeEnum;
import com.aditya.xlabs.factory.SpaceShip;

public class BClassShip extends SpaceShip {

	public BClassShip(int countOfShotsThisShipCanBear) {
		super(SpaceShipTypeEnum.BCLASS);
		setShotsThisShipCanBear(countOfShotsThisShipCanBear);
	}

	@Override
	protected void addToSpaceShip() {
		System.out.println("Building B-Class Ship");
	}

}
