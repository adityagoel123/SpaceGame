package com.aditya.xlabs.GameService.impl;

import java.util.Arrays;
import java.util.Collections;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.aditya.xlabs.Entity.Game;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.GameService.IGameService;

public class GameServiceImplTest {
	
	IGameService gameService = new GameServiceImpl(); 

	@Test
	public void testWhetherGameGettingInitialize_instantiateNewGame() {
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		XLSpaceShipInstance x2 = Mockito.mock(XLSpaceShipInstance.class);
		Game gameInstance = gameService.instantiateNewGame(Arrays.asList(x1,x2));
		Assert.assertNotNull("Instance must be intialized", gameInstance);
	}
	
	
	// Requirement Says, A XlSpaceeShipInstance can participate in multiple games at a given moment of time.
	@Test
	public void newGameShouldBeInitializedForAlreadyPlayingInstances_instantiateNewGame() {
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		XLSpaceShipInstance x2 = Mockito.mock(XLSpaceShipInstance.class);
		
		Game gameInstance1 = gameService.instantiateNewGame(Arrays.asList(x1,x2));
		Game gameInstance2 = gameService.instantiateNewGame(Arrays.asList(x1,x2));

		Assert.assertNotSame("GameID must be Uniquee & different..", gameInstance1.getGameID(), gameInstance2.getGameID());
	}
	
	
	@Test
	public void gameIdMustBeUniqueForDifferentRequests_instantiateNewGame() {
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		XLSpaceShipInstance x2 = Mockito.mock(XLSpaceShipInstance.class);
		
		XLSpaceShipInstance x3 = Mockito.mock(XLSpaceShipInstance.class);
		XLSpaceShipInstance x4 = Mockito.mock(XLSpaceShipInstance.class);
		
		Game gameInstance1 = gameService.instantiateNewGame(Arrays.asList(x1,x2));
		Game gameInstance2 = gameService.instantiateNewGame(Arrays.asList(x3,x4));

		Assert.assertEquals("GameID must be different", false, gameInstance1.getGameID().equalsIgnoreCase(gameInstance2.getGameID()));
	}
	
	@Test
	public void gameMustNotInitializeWithLessThanAtleast2Instances_instantiateNewGame() {
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		Game gameInstance = gameService.instantiateNewGame(Arrays.asList(x1));
		Assert.assertNull("Instance must NOT be intialized", gameInstance);
	}
	
	@Test
	public void gameMustNotInitializeNoInstances_instantiateNewGame() {
		Game gameInstance = gameService.instantiateNewGame(Collections.<XLSpaceShipInstance> emptyList());
		Assert.assertNull("Instance must NOT be intialized", gameInstance);
	}
	
	
	@Test
	public void gameMustNotInitializeWithoutInstances_instantiateNewGame() {
		Game gameInstance = gameService.instantiateNewGame(null);
		Assert.assertNull("Instance must NOT be intialized", gameInstance);
	}
	

	@Test(expected=NullPointerException.class)
	public void checkUpdateTurnWithNullGame_updateNextTurnInActiveGame() {
		gameService.updateNextTurnInActiveGame(null);
	}
	
	@Test
	public void currentPlayerTurnMustBeUpdated_updateNextTurnInActiveGame() {
		Game g1 = Mockito.mock(Game.class);
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		XLSpaceShipInstance x2 = Mockito.mock(XLSpaceShipInstance.class);
		Mockito.when(g1.getCurrenlyPlayingInstances()).thenReturn(Arrays.asList(x1,x2));
		Mockito.when(g1.getCurrenlyPlayingInstances().get(0).isNextTurnBelongsToThisInstance()).thenReturn(true);
		gameService.updateNextTurnInActiveGame(g1);
		Assert.assertEquals("Current Turn must be eupdated", false, x2.isNextTurnBelongsToThisInstance());
	}
	
	
}
