package com.aditya.xlabs.Entity;

import com.aditya.xlabs.enums.SpaceShipTypeEnum;
import com.aditya.xlabs.factory.SpaceShip;

public class AngleShip extends SpaceShip {

	public AngleShip(int countOfShotsThisShipCanBear) {
		super(SpaceShipTypeEnum.ANGLE);
		setShotsThisShipCanBear(countOfShotsThisShipCanBear);
	}

	@Override
	protected void addToSpaceShip() {
		System.out.println("Building Angle Ship");
	}

}
