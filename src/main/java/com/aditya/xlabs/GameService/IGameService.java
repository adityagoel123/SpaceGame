package com.aditya.xlabs.GameService;

import java.util.List;

import com.aditya.xlabs.Entity.Game;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;


public interface IGameService {

	Game instantiateNewGame(List<XLSpaceShipInstance> listOfInstances);
	
	void updateNextTurnInActiveGame(Game currentlyActiveGame);

}
