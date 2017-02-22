package com.aditya.xlabs.SpaceService.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.aditya.xlabs.Entity.Game;
import com.aditya.xlabs.Entity.Player;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.GameService.IGameService;
import com.aditya.xlabs.GameService.impl.GameServiceImpl;
import com.aditya.xlabs.GridService.IGridService;
import com.aditya.xlabs.GridService.IVisualGridGenerationService;
import com.aditya.xlabs.GridService.impl.GridServiceImpl;
import com.aditya.xlabs.GridService.impl.VisualGridGenerationServiceImpl;
import com.aditya.xlabs.PlayerService.IPlayerService;
import com.aditya.xlabs.PlayerService.impl.PlayerServiceImpl;
import com.aditya.xlabs.Request.RequestForFiringSalvoOfShots;
import com.aditya.xlabs.Request.RequestForNewGame;
import com.aditya.xlabs.Response.ResponseForFiredSalvoOfShots;
import com.aditya.xlabs.Response.ResponseForGameStatus;
import com.aditya.xlabs.Response.ResponseForNewGame;
import com.aditya.xlabs.RuleService.IRuleService;
import com.aditya.xlabs.RuleService.impl.RuleServiceImpl;
import com.aditya.xlabs.SpaceService.ISpaceShipService;
import com.aditya.xlabs.XLSpaceShipInstanceService.IXLShipInstanceService;
import com.aditya.xlabs.XLSpaceShipInstanceService.impl.XLShipInstanceServiceImpl;
import com.aditya.xlabs.controller.XLWebServiceController;
import com.aditya.xlabs.enums.GameRuleTypeEnum;
import com.aditya.xlabs.enums.ShipStatusTypeEnum;
import com.aditya.xlabs.factory.SpaceShip;
import com.aditya.xlabs.utilities.IConvertorService;
import com.aditya.xlabs.utilities.impl.ConvertorServiceImpl;


public class SpaceShipServiceImpl implements ISpaceShipService {

	IPlayerService playerService = new PlayerServiceImpl();
	IGameService gameService = new GameServiceImpl();
	IConvertorService convService = new ConvertorServiceImpl();
	IVisualGridGenerationService gridGenerator = new VisualGridGenerationServiceImpl();
	IRuleService ruleService = new RuleServiceImpl();
	IGridService gridService = new GridServiceImpl();
	
	IXLShipInstanceService  xlShipService = new XLShipInstanceServiceImpl();
	
	public ResponseForNewGame generateResponseForNewGame(RequestForNewGame request) {
		// Instantiate the Computer Player(Me-- The Challenged Instance) determined at random. 
		// Instantiate the Game.
		// Populate the Starting Player User Id & fullName into the response object.
		Random random = new Random();
		
		ResponseForNewGame response = new ResponseForNewGame();
		
		Player challengedPlayer = playerService.getSelfPlayer();
		XLSpaceShipInstance challengedInstance = xlShipService.instantiateXLSpaceShipInstance(challengedPlayer, "127.0.0.1", "9090");
		for(String s : challengedInstance.getPosnToShipsMap().keySet())
			System.out.print("Player" + s +"\t");
		// Assign Default Name & ID to the Challenging (Requesting) player.
		String challengingPlayerDefaultName = request.getFull_name();
		String challengingPlayerDefaultId = request.getUser_id();
		if(null == request.getUser_id()){
			AtomicInteger uniquePlayerID = new AtomicInteger();
			challengingPlayerDefaultId = "player-" + uniquePlayerID.addAndGet(10);
		}
		if(null == request.getFull_name()){
			challengingPlayerDefaultName = "challenging "+ challengingPlayerDefaultId;
		}
		
		Player challengingPlayer = new Player(challengingPlayerDefaultId, challengingPlayerDefaultName);
		XLSpaceShipInstance challengingInstance = xlShipService.instantiateXLSpaceShipInstance(challengingPlayer, 
				request.getSpaceship_protocol().getHostname(), request.getSpaceship_protocol().getPort());
		for(String s : challengingInstance.getPosnToShipsMap().keySet())
			System.out.print("Challenging Player" + s +"\t");
		
		List<XLSpaceShipInstance> instancesToPlayGame = new ArrayList<XLSpaceShipInstance>();
		instancesToPlayGame.add(challengedInstance);
		instancesToPlayGame.add(challengingInstance);
		
		Game newGame = gameService.instantiateNewGame(instancesToPlayGame);
		newGame.setRules(request.getRules());
		
		XLWebServiceController.activeGamesStore.put(newGame.getGameID(), newGame);
		
		response.setUser_id(challengedPlayer.getUserId());
		response.setFull_name(challengedPlayer.getFullName());
		response.setGame_id(newGame.getGameID());
		
		// Determine the Starting Player at Random.
		XLSpaceShipInstance randomStartingPlayer = instancesToPlayGame.get(random.nextInt(instancesToPlayGame.size()));
		randomStartingPlayer.setNextTurnBelongsToThisInstance(true);
		response.setStarting(randomStartingPlayer.getOwnerOfInstance().getUserId());
		// Set The Rules attribute in Response. If its null, Set standard.
		response.setRules(request.getRules() != null ? request.getRules() : GameRuleTypeEnum.STANDARD.code());
		return response;
	}
	

	public ResponseForFiredSalvoOfShots fireSalvoOfShotsAt(RequestForFiringSalvoOfShots request, 
			XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed, XLSpaceShipInstance instanceWithCurrentTurn, String gameType) {
		ResponseForFiredSalvoOfShots response = new ResponseForFiredSalvoOfShots();
		// Fetch the Indexes at which shots are fired at by the Current Player.
		List<String> indexesFiredAt = convService.convertShotsToIndexes(request.getSalvo());
		
		// Fetch the Indexes at which Ships of "instanceWhoseShipsAreToBeDestroyed" are lying. 
		Set<String> indexeContainingShipsOfOpponent = instanceWhoseShipsAreToBeDestroyed.getPosnToShipsMap().keySet();
		List<String> indexesContainingShipsOfOpponent = new ArrayList<String>(indexeContainingShipsOfOpponent);
		indexeContainingShipsOfOpponent = null;
		
		for(String indexFired : indexesFiredAt){
			if(indexesContainingShipsOfOpponent.contains(indexFired.toLowerCase())){
				SpaceShip s = instanceWhoseShipsAreToBeDestroyed.getPosnToShipsMap().get(indexFired.toLowerCase());
				s.setShotsThisShipCanBear(s.getShotsThisShipCanBear()-1);
				if(s.getShotsThisShipCanBear()==0 && (null != gameType) && gameType.equalsIgnoreCase(GameRuleTypeEnum.DESPERATION.code())){
					instanceWithCurrentTurn.setEligibleCountOfShotsForNextTurn(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn()+1);
				} else if(s.getShotsThisShipCanBear()==0 && (null != gameType) && gameType.equalsIgnoreCase(GameRuleTypeEnum.SUPERCHARGE.code())){
					instanceWithCurrentTurn.setEligibleCountOfShotsForNextTurn(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn() + request.getSalvo().size());
				}
			}
		}
		populatePositionOfShipToUpdatedStatusOfShip(response.getSalvo(),instanceWhoseShipsAreToBeDestroyed,indexesFiredAt);
		return response;
	}


	public void populatePositionOfShipToUpdatedStatusOfShip(Map<String, String> salvo, XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed, List<String> indexesFiredAt) {
		for(String indexFired : indexesFiredAt){
			String statusOfShip = findStatusOfShip(indexFired,instanceWhoseShipsAreToBeDestroyed);
			salvo.put(indexFired, statusOfShip);
		}
	}
	
	public String findStatusOfShip(String index, XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed) {
		if(null != instanceWhoseShipsAreToBeDestroyed && null != index){
			if(null == instanceWhoseShipsAreToBeDestroyed.getPosnToShipsMap().get(index.toLowerCase()) || instanceWhoseShipsAreToBeDestroyed.getPosnToShipsMap().get(index.toLowerCase()).getShotsThisShipCanBear()==2)
				return ShipStatusTypeEnum.MISS.code();
			else if(instanceWhoseShipsAreToBeDestroyed.getPosnToShipsMap().get(index.toLowerCase()).getShotsThisShipCanBear()==1)
				return ShipStatusTypeEnum.HIT.code();
			else if (instanceWhoseShipsAreToBeDestroyed.getPosnToShipsMap().get(index.toLowerCase()).getShotsThisShipCanBear() < 1)
				return ShipStatusTypeEnum.KILL.code();
			else 
				return ShipStatusTypeEnum.KILL.code();
		} else
			return ShipStatusTypeEnum.NOT_FOUND.code();
	}

	public XLSpaceShipInstance findInstanceWhoseShipsAreToBeDestroyed(Game currentlyActiveGame) {
		// Fetch the Opponent Instance, whose ships are to be destroyed.
		if(null != currentlyActiveGame){
			XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed = new XLSpaceShipInstance();
			List<XLSpaceShipInstance> listOfInstances = currentlyActiveGame.getCurrenlyPlayingInstances();
			
			for(int i=0; i< listOfInstances.size(); i++){
				if(listOfInstances.get(i).isNextTurnBelongsToThisInstance()){
					if(i==listOfInstances.size()-1){
						instanceWhoseShipsAreToBeDestroyed = listOfInstances.get(0);
						break;
					} else {
						instanceWhoseShipsAreToBeDestroyed = listOfInstances.get(i+1);
						break;
					}
				}
			}
			return instanceWhoseShipsAreToBeDestroyed;
		} else {
			return null;
		}
	}

	public XLSpaceShipInstance findInstanceWithCurrentTurn(Game currentlyActiveGame) {
		// Fetch the Opponent Instance, whose current Turn is NOW.
		if(null != currentlyActiveGame){
			XLSpaceShipInstance instanceWithCurrentTurn = null;
			List<XLSpaceShipInstance> listOfInstances = currentlyActiveGame.getCurrenlyPlayingInstances();
			
			for(int i=0; i< listOfInstances.size(); i++){
				if(listOfInstances.get(i).isNextTurnBelongsToThisInstance()){
					instanceWithCurrentTurn = listOfInstances.get(i);
					break;
				}
			}
			return instanceWithCurrentTurn;	
		} else {
			return null;
		}
	}
	


	public void determineNextTurnOrGameStatus(ResponseForFiredSalvoOfShots response, Game currentlyActiveGame, XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed, XLSpaceShipInstance instanceWithCurrentTurn) {
		if(null == currentlyActiveGame.getRules() || currentlyActiveGame.getRules().equalsIgnoreCase(GameRuleTypeEnum.STANDARD.code()) || 
				currentlyActiveGame.getRules().contains("-shot")){
			// Evaluate the Instance whose ships were destroyed.
			boolean gameLostByThisInstance = true;
			Collection<SpaceShip> listOfShipsOfThisInstance = instanceWhoseShipsAreToBeDestroyed.getPosnToShipsMap().values();
			Iterator<SpaceShip> itr = listOfShipsOfThisInstance.iterator();
		    while(itr.hasNext()) {
		      if(itr.next().getShotsThisShipCanBear() > 0){
		    	  gameLostByThisInstance = false;
		    	  break;
		      }
			}
		    if(gameLostByThisInstance){
		    	response.getGame().put("won", instanceWithCurrentTurn.getOwnerOfInstance().getUserId());
		    	currentlyActiveGame.setEndTime(new Date());
		    } else {
		    	response.getGame().put("player_turn", instanceWhoseShipsAreToBeDestroyed.getOwnerOfInstance().getUserId());
		    	gameService.updateNextTurnInActiveGame(currentlyActiveGame);
		    }
		} else if(currentlyActiveGame.getRules().equalsIgnoreCase(GameRuleTypeEnum.SUPERCHARGE.code())){
			if(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn()>0){
				response.getGame().put("player_turn", instanceWithCurrentTurn.getOwnerOfInstance().getUserId());
			} else {
				// Miss Case
				response.getGame().put("player_turn", instanceWhoseShipsAreToBeDestroyed.getOwnerOfInstance().getUserId());
		    	gameService.updateNextTurnInActiveGame(currentlyActiveGame);
			}
		} else if(currentlyActiveGame.getRules().equalsIgnoreCase(GameRuleTypeEnum.DESPERATION.code())){
			// Find If This instance destroyed any ship. Give Extra shot in lieu of the same.
			if(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn()>0){
				response.getGame().put("player_turn", instanceWithCurrentTurn.getOwnerOfInstance().getUserId());
			} else {
				response.getGame().put("player_turn", instanceWhoseShipsAreToBeDestroyed.getOwnerOfInstance().getUserId());
		    	gameService.updateNextTurnInActiveGame(currentlyActiveGame);
			}
		} 
	}

	public ResponseForGameStatus findStatusOfGame(Game activeGame, String gameID) {
		ResponseForGameStatus response = new ResponseForGameStatus();
		List<XLSpaceShipInstance> currentActiveInstances = activeGame.getCurrenlyPlayingInstances();
		XLSpaceShipInstance selfPlayer = null;
		XLSpaceShipInstance opponentPlayer = null;
		
		for(XLSpaceShipInstance xl : currentActiveInstances){
			if(xl.getOwnerOfInstance().getFullName().equalsIgnoreCase("AssesmentPlayer")){
				selfPlayer = xl;
			} else if(xl.getOwnerOfInstance().getFullName().equalsIgnoreCase("XebiaLabs Opponent")){
				opponentPlayer = xl;
			}
		}
		
		response.getSelf().put("user_id",selfPlayer.getOwnerOfInstance().getUserId());
		response.getSelf().put("board",gridGenerator.generateGridForThisInstance(selfPlayer.getPosnToShipsMap()));
		
		response.getOpponent().put("user_id", opponentPlayer.getOwnerOfInstance().getUserId());
		response.getOpponent().put("board",gridGenerator.generateGridForThisInstance(new HashMap<String, SpaceShip>()));
		
		boolean isGameOver;
		if(activeGame.getEndTime() == null)
			isGameOver = false; 
		else 
			isGameOver = true;
		
		if(isGameOver){
			// Find Out Who won it.
			for(int i=0; i<activeGame.getCurrenlyPlayingInstances().size();i++){
				Collection<SpaceShip> listOfShipsOfThisInstance = activeGame.getCurrenlyPlayingInstances().get(i).getPosnToShipsMap().values();
				Iterator<SpaceShip> itr = listOfShipsOfThisInstance.iterator();
			    while(itr.hasNext()) {
			      if(itr.next().getShotsThisShipCanBear() <= 0){
			    	  if(i==activeGame.getCurrenlyPlayingInstances().size()-1){
			    		  response.getGame().put("won", activeGame.getCurrenlyPlayingInstances().get(0).getOwnerOfInstance().getUserId());
			    	  } else {
			    		  response.getGame().put("won", activeGame.getCurrenlyPlayingInstances().get(i+1).getOwnerOfInstance().getUserId());
			    	  }
			    	  break;
			      }
				}
			}

		} else {
			if(selfPlayer.isNextTurnBelongsToThisInstance()==true)
				response.getGame().put("player_turn", selfPlayer.getOwnerOfInstance().getUserId());
			else if(opponentPlayer.isNextTurnBelongsToThisInstance()==true)
				response.getGame().put("player_turn", opponentPlayer.getOwnerOfInstance().getUserId());
		}
		/*
	    listOfShipsOfThisInstance = null;
	    itr = null;
	    
	    listOfShipsOfThisInstance = opponentPlayer.getPosnToShipsMap().values();
		itr = listOfShipsOfThisInstance.iterator();
	    while(itr.hasNext()) {
	      if(itr.next().getShotsThisShipCanBear() > 0){
	    	  isGameOver = false;
	    	  break;
	      }
		}*/
	    
		return response;
		
	}


	public void startAutoPilotMode(Game game) {
		while (null == game.getEndTime()) {
			// Find The Instance with current turn And Whose Ships are To be destroyed. 
			XLSpaceShipInstance instanceWithCurrentTurn = findInstanceWithCurrentTurn(game);
			XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed = findInstanceWhoseShipsAreToBeDestroyed(game);
			// Randomly Prepare the No. of shots equal to no. of its live ships.
			int countOfAllowedShots = ruleService.calculateAllowedCountOfShots(
					instanceWithCurrentTurn, game.getRules());
			List<String> listOfApplicableShots = gridService
					.generateShotsToGridRandomly(countOfAllowedShots);
			// Fire them at opponent's Ships AND Update Next Turn
			determineNextTurnOrGameStatus(fireSalvoOfShotsAt(new RequestForFiringSalvoOfShots(listOfApplicableShots),
					instanceWhoseShipsAreToBeDestroyed, instanceWithCurrentTurn, game.getRules()), 
					game, instanceWhoseShipsAreToBeDestroyed, instanceWithCurrentTurn);
		}
		// Repeat above process till Game has not ended over.
	}
}
