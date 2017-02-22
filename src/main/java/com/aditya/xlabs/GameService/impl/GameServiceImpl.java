package com.aditya.xlabs.GameService.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.aditya.xlabs.Entity.Game;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.GameService.IGameService;

public class GameServiceImpl implements IGameService {

	static AtomicInteger uniqueGameID = new AtomicInteger();
	
	public Game instantiateNewGame(List<XLSpaceShipInstance> listOfInstances) {
		if(null !=listOfInstances && listOfInstances.size()>=2){
			Game newGame = new Game();
			newGame.setStartTime(new Date());
			newGame.setGameID("match-" + uniqueGameID.addAndGet(1));
			newGame.setCurrenlyPlayingInstances(listOfInstances);
			return newGame;
		} else {
			return null;
		}
	}
	
	public void updateNextTurnInActiveGame(Game currentlyActiveGame) {
		// Fetch the Instance, with current Turn
		List<XLSpaceShipInstance> listOfInstances = currentlyActiveGame.getCurrenlyPlayingInstances();
		
		for(int i=0; i< listOfInstances.size(); i++){
			if(listOfInstances.get(i).isNextTurnBelongsToThisInstance()){
				listOfInstances.get(i).setNextTurnBelongsToThisInstance(false);
				if(i==listOfInstances.size()-1){
					listOfInstances.get(0).setNextTurnBelongsToThisInstance(true);
					break;
				} else {
					listOfInstances.get(i+1).setNextTurnBelongsToThisInstance(true);
					break;
				}
			}
		}
	
	}
	
}
