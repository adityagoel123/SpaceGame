package com.aditya.xlabs.SpaceService;

import java.util.List;
import java.util.Map;

import com.aditya.xlabs.Request.RequestForFiringSalvoOfShots;
import com.aditya.xlabs.Request.RequestForNewGame;
import com.aditya.xlabs.Entity.Game;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.Response.ResponseForFiredSalvoOfShots;
import com.aditya.xlabs.Response.ResponseForGameStatus;
import com.aditya.xlabs.Response.ResponseForNewGame;

public interface ISpaceShipService {

	ResponseForNewGame generateResponseForNewGame(RequestForNewGame request);

	ResponseForFiredSalvoOfShots fireSalvoOfShotsAt(RequestForFiringSalvoOfShots request, XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed, XLSpaceShipInstance instanceWithCurrentTurn, String gameType);

	XLSpaceShipInstance findInstanceWhoseShipsAreToBeDestroyed(Game currentlyActiveGame);

	void determineNextTurnOrGameStatus(ResponseForFiredSalvoOfShots response, Game currentlyActiveGame, XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed, XLSpaceShipInstance instanceWithCurrentTurn);

	XLSpaceShipInstance findInstanceWithCurrentTurn(Game currentlyActiveGame);

	ResponseForGameStatus findStatusOfGame(Game game, String gameID);

	void startAutoPilotMode(Game game);
	
	String findStatusOfShip(String index, XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed);
	
	void populatePositionOfShipToUpdatedStatusOfShip(Map<String, String> salvo, XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed, List<String> indexesFiredAt);


	
}
