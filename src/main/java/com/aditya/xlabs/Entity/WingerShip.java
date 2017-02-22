package com.aditya.xlabs.Entity;

import com.aditya.xlabs.enums.SpaceShipTypeEnum;
import com.aditya.xlabs.factory.SpaceShip;

public class WingerShip extends SpaceShip {

	public WingerShip(int countOfShotsThisShipCanBear) {
		super(SpaceShipTypeEnum.WINGER);
		setShotsThisShipCanBear(countOfShotsThisShipCanBear);
	}

	@Override
	protected void addToSpaceShip() {
		System.out.println("Building Winger Ship");
	}

}
