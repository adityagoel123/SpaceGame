package com.aditya.xlabs.ValidationService.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.aditya.xlabs.DataGenerator.ManualDataObjectCreator;
import com.aditya.xlabs.Entity.Game;
import com.aditya.xlabs.Request.RequestForFiringSalvoOfShots;
import com.aditya.xlabs.Request.RequestForNewGame;
import com.aditya.xlabs.ValidationService.IValidationService;
import com.aditya.xlabs.controller.XLWebServiceController;
import com.aditya.xlabs.factory.SpaceShip;

public class ValidationServiceImplTest {

	IValidationService validator = new ValidationServiceImpl();
	ManualDataObjectCreator dataGenerator = new ManualDataObjectCreator();
	
	@Test
	public void testPositive_validateRequestForNewGame(){
		RequestForNewGame req = dataGenerator.requestForNewGameGenerator_FullyLoadedRequest();
		boolean result = validator.validateRequestForNewGame(req, "PROTOCOL_API");
		Assert.assertTrue("Request should be validated Positive", result);
	}
	
	@Test
	public void testNullRequest_validateRequestForNewGame(){
		boolean result = validator.validateRequestForNewGame(null, "PROTOCOL_API");
		Assert.assertTrue("Request should be validated Positive", !result);
	}
	
	@Test
	public void testNegative_validateRequestForNewGame(){
		RequestForNewGame req = dataGenerator.requestForNewGameGenerator_NotContainsProtocolDetails();
		boolean result = validator.validateRequestForNewGame(req, "PROTOCOL_API");
		Assert.assertTrue("Request should NOT be validated Positive, as protocol details missin ", !result);
	}
	
	@Test
	public void testNegativeAnother_validateRequestForNewGame(){
		RequestForNewGame req = dataGenerator.requestForNewGameGenerator_NotContainsOpponentPlayersDeetails();
		boolean result = validator.validateRequestForNewGame(req, "PROTOCOL_API");
		Assert.assertTrue("Request should NOT be validated Positive, as Opponent Players details missin ", !result);
	}
	
	@Test
	public void testNegativeRulesMissing_validateRequestForNewGame(){
		RequestForNewGame req = dataGenerator.requestForNewGameGenerator_NOTContainsRuleType();
		boolean result = validator.validateRequestForNewGame(req, "PROTOCOL_API");
		Assert.assertTrue("Request should BE validated Positive, as Passing RuleType is Optional ", result);
	}
	
	
	
	
	@Test
	public void testPositive_validateRequestForFiringShots(){
		RequestForFiringSalvoOfShots req = new RequestForFiringSalvoOfShots(Arrays.asList("1b","d3","c0","69"));
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestForFiringShots(req, "match-1");
		Assert.assertTrue("Request should be validated Positive", result);
	}
	
	
	@Test
	public void testAnotherPositive_validateRequestForFiringShots(){
		RequestForFiringSalvoOfShots req = new RequestForFiringSalvoOfShots(Arrays.asList("1b","c0","69"));
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestForFiringShots(req, "match-1");
		Assert.assertTrue("Request should be validated Positive", result);
	}

	@Test
	public void testNoSalvoShots_validateRequestForFiringShots(){
		RequestForFiringSalvoOfShots req = new RequestForFiringSalvoOfShots();
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestForFiringShots(req, "match-1");
		Assert.assertTrue("Request should NOT be validated Positive as there are empty salvo of shots", !result);
	}

	@Test
	public void testNegativeDoesntContainsGameId_validateRequestForFiringShots(){
		RequestForFiringSalvoOfShots req = new RequestForFiringSalvoOfShots();
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("")).thenReturn(sampleGame);
		boolean result = validator.validateRequestForFiringShots(req, "match-1");
		Assert.assertTrue("Request should NOT be validated Positive as there is NO GameID provided", !result);
	}

	@Test
	public void testNegativeGameDoesntExists_validateRequestForFiringShots(){
		RequestForFiringSalvoOfShots req = new RequestForFiringSalvoOfShots();
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("abc-Any")).thenReturn(sampleGame);
		boolean result = validator.validateRequestForFiringShots(req, "match-1");
		Assert.assertTrue("Request should NOT be validated Positive as there is NO GameID provided", !result);
	}

	
	@Test
	public void testPositive_validateRequestForGameStatus(){
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestForGameStatus("match-1");
		Assert.assertNotSame("Request should BE validated Positive, as Game is valid & exists. ", false, result);
	}
	
	
	@Test
	public void testNegativeNullGame_validateRequestForGameStatus(){
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("abs")).thenReturn(sampleGame);
		boolean result = validator.validateRequestForGameStatus("match-1");
		Assert.assertNotSame("Request should NOT BE validated Positive, as Game is valid & exists. ", true, result);
	}
	
	@Test
	public void testNegateEmptyGameID_validateRequestForGameStatus(){
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestForGameStatus("");
		Assert.assertTrue("Request should BE validated Positive, as Game is valid & exists. ", !result);
	}
	
	@Test
	public void testNegateNullGameID_validateRequestForGameStatus(){
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestForGameStatus(null);
		Assert.assertTrue("Request should BE validated Positive, as Game is valid & exists. ", !result);
	}
	
	

	
	@Test
	public void testPositive_validateRequestFromCorrectSource(){
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestFromCorrectSource("match-1", "74.1.9.23", "2349");
		Assert.assertTrue("Request should BE validated Positive, request is from correct source.", result);
	}
	
	
	@Test
	public void testNegateIPAddrssDifferent_validateRequestFromCorrectSource(){
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestFromCorrectSource("match-1", "74.10.9.23", "2349");
		Assert.assertTrue("Request should NOT BE validated Positive, request is from correct source.", !result);
	}
	
	
	@Test
	public void testNegatePortDifferent_validateRequestFromCorrectSource(){
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestFromCorrectSource("match-1", "74.1.9.23", "2319");
		Assert.assertTrue("Request should NOT BE validated Positive, request is from correct source.", !result);
	}
	
	@Test
	public void testNegateGameIDMissing_validateRequestFromCorrectSource(){
		Game sampleGame = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		XLWebServiceController.activeGamesStore = Mockito.mock(Map.class);
		Mockito.when(XLWebServiceController.activeGamesStore.get("match-1")).thenReturn(sampleGame);
		boolean result = validator.validateRequestFromCorrectSource("", "74.1.9.23", "2319");
		Assert.assertTrue("Request should NOT BE validated Positive, request GAMEID missin.", !result);
	}
	
}
