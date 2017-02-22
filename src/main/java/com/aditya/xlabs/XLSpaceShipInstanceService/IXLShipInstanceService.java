package com.aditya.xlabs.XLSpaceShipInstanceService;

import com.aditya.xlabs.Entity.Player;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;

public interface IXLShipInstanceService {

	public XLSpaceShipInstance instantiateXLSpaceShipInstance(Player owner, String ipAdd, String port);
		
	public void placeShipsAtRandomPositionsInGrid(XLSpaceShipInstance instance);
}
