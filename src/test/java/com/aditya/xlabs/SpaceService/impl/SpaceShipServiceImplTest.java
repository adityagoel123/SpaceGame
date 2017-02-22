package com.aditya.xlabs.SpaceService.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

//import org.apache.catalina.connector.Request;
import org.junit.Test;
import org.mockito.Mockito;

import com.aditya.xlabs.APIType.ProtocolAPI;
import com.aditya.xlabs.DataGenerator.ManualDataObjectCreator;
import com.aditya.xlabs.Entity.AClassShip;
import com.aditya.xlabs.Entity.BClassShip;
import com.aditya.xlabs.Entity.Game;
import com.aditya.xlabs.Entity.Player;
import com.aditya.xlabs.Entity.SClassShip;
import com.aditya.xlabs.Entity.WingerShip;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.Request.RequestForFiringSalvoOfShots;
import com.aditya.xlabs.Request.RequestForNewGame;
import com.aditya.xlabs.Response.ResponseForFiredSalvoOfShots;
import com.aditya.xlabs.Response.ResponseForNewGame;
import com.aditya.xlabs.SpaceService.ISpaceShipService;
import com.aditya.xlabs.factory.SpaceShip;

public class SpaceShipServiceImplTest {
	ManualDataObjectCreator dataGenerator = new ManualDataObjectCreator();
	ISpaceShipService spaceService = new SpaceShipServiceImpl();
	
	@Test
	public void checkPositive_generateResponseForNewGame(){
		RequestForNewGame req = dataGenerator.requestForNewGameGenerator_FullyLoadedRequest();
		ResponseForNewGame res = spaceService.generateResponseForNewGame(req);
		Assert.assertNotNull("Self Player should not be empty", res.getFull_name());
		Assert.assertNotNull("GameID must NOT be null", res.getGame_id());
		Assert.assertNotSame("Self Player shud have a ID", "", res.getUser_id());
	}
	
	@Test(expected=Exception.class)
	public void checkNegativeNOTPassingProtocolAPI_generateResponseForNewGame(){
		RequestForNewGame req = dataGenerator.requestForNewGameGenerator_NotContainsProtocolDetails();
		ResponseForNewGame res = spaceService.generateResponseForNewGame(req);
	}
	
	@Test
	public void testPositive_findStatusOfShip(){
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		Map<String,SpaceShip> posnToShipMapForX1 = new HashMap<String, SpaceShip>();
		posnToShipMapForX1.put("02", new WingerShip(2));
		posnToShipMapForX1.put("ae", new AClassShip(2));
		posnToShipMapForX1.put("8b", new BClassShip(2));
		posnToShipMapForX1.put("9d", new SClassShip(2));
		Mockito.when(x1.getPosnToShipsMap()).thenReturn(posnToShipMapForX1);
		String result = spaceService.findStatusOfShip("02", x1);
		Assert.assertEquals("If No. of Shots A Ship can bear > 2, MISS shud return", "MISS", result);
	}
	
	@Test
	public void testCasing_findStatusOfShip(){
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		Map<String,SpaceShip> posnToShipMapForX1 = new HashMap<String, SpaceShip>();
		posnToShipMapForX1.put("02", new WingerShip(2));
		posnToShipMapForX1.put("ae", new AClassShip(2));
		posnToShipMapForX1.put("8b", new BClassShip(2));
		posnToShipMapForX1.put("9d", new SClassShip(2));
		Mockito.when(x1.getPosnToShipsMap()).thenReturn(posnToShipMapForX1);
		String result = spaceService.findStatusOfShip("AE", x1);
		Assert.assertEquals("CASING of INDEX mustn't cause issue, hence > 2, MISS shud return", "MISS", result);
	}
	
	@Test
	public void testAnotherPositive_findStatusOfShip(){
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		Map<String,SpaceShip> posnToShipMapForX1 = new HashMap<String, SpaceShip>();
		posnToShipMapForX1.put("02", new WingerShip(2));
		posnToShipMapForX1.put("ae", new AClassShip(2));
		posnToShipMapForX1.put("8b", new BClassShip(2));
		posnToShipMapForX1.put("9d", new SClassShip(0));
		Mockito.when(x1.getPosnToShipsMap()).thenReturn(posnToShipMapForX1);
		String result = spaceService.findStatusOfShip("9d", x1);
		Assert.assertEquals("If No. of Shots A Ship can bear = 0, KILL shud return", "KILL", result);
	}
	
	@Test
	public void testAnotheNegative_findStatusOfShip(){
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		Map<String,SpaceShip> posnToShipMapForX1 = new HashMap<String, SpaceShip>();
		posnToShipMapForX1.put("02", new WingerShip(2));
		posnToShipMapForX1.put("ae", new AClassShip(2));
		posnToShipMapForX1.put("8b", new BClassShip(2));
		posnToShipMapForX1.put("9d", new SClassShip(0));
		Mockito.when(x1.getPosnToShipsMap()).thenReturn(posnToShipMapForX1);
		String result = spaceService.findStatusOfShip("", x1);
		Assert.assertEquals("No Ship is lying @ index empty", "MISS", result);
	}
	
	@Test
	public void testNullInstance_findStatusOfShip(){
		String result = spaceService.findStatusOfShip("sd", null);
		Assert.assertEquals("No Ship is lying @ index empty", "NOT_FOUND", result);
	}
	
	@Test
	public void testNullIndex_findStatusOfShip(){
		XLSpaceShipInstance x1 = Mockito.mock(XLSpaceShipInstance.class);
		Map<String,SpaceShip> posnToShipMapForX1 = new HashMap<String, SpaceShip>();
		posnToShipMapForX1.put("02", new WingerShip(2));
		posnToShipMapForX1.put("ae", new AClassShip(2));
		posnToShipMapForX1.put("8b", new BClassShip(2));
		posnToShipMapForX1.put("9d", new SClassShip(0));
		Mockito.when(x1.getPosnToShipsMap()).thenReturn(posnToShipMapForX1);
		String result = spaceService.findStatusOfShip(null, x1);
		Assert.assertEquals("No Ship is lying @ index empty", "NOT_FOUND", result);
	}
	
	@Test
	public void testFetchInstance_findInstanceWithCurrentTurn(){
		Game g = Mockito.mock(Game.class);
		XLSpaceShipInstance x1 = new XLSpaceShipInstance();
		x1.setNextTurnBelongsToThisInstance(false);
		XLSpaceShipInstance x2 = new XLSpaceShipInstance();
		x2.setNextTurnBelongsToThisInstance(true);
		List<XLSpaceShipInstance> currentInstances = Arrays.asList(x1,x2);
		Mockito.when(g.getCurrenlyPlayingInstances()).thenReturn(currentInstances);
		
		XLSpaceShipInstance result = spaceService.findInstanceWithCurrentTurn(g);
		Assert.assertTrue("CurrentTurn of this player returned true", result.isNextTurnBelongsToThisInstance());
		
	}
	
	@Test
	public void testWithNullInstance_findInstanceWithCurrentTurn(){
		XLSpaceShipInstance result = spaceService.findInstanceWithCurrentTurn(null);
		Assert.assertNull("Null Instance expected", result);
	}
	
	@Test
	public void testWithNegativeInput_findInstanceWithCurrentTurn(){
		Game g = Mockito.mock(Game.class);
		XLSpaceShipInstance x1 = new XLSpaceShipInstance();
		x1.setNextTurnBelongsToThisInstance(false);
		XLSpaceShipInstance x2 = new XLSpaceShipInstance();
		x2.setNextTurnBelongsToThisInstance(false);
		List<XLSpaceShipInstance> currentInstances = Arrays.asList(x1,x2);
		Mockito.when(g.getCurrenlyPlayingInstances()).thenReturn(currentInstances);
		
		XLSpaceShipInstance result = spaceService.findInstanceWithCurrentTurn(g);
		Assert.assertNull("Null Instance expected, as both instaances have turned ON NEXT Turn..Not Possbl conditon", result);
	}

	@Test
	public void testFetchInstance_findInstanceWhoseShipsAreToBeDestroyed(){
		Game g = Mockito.mock(Game.class);
		XLSpaceShipInstance x1 = new XLSpaceShipInstance();
		x1.setNextTurnBelongsToThisInstance(false);
		XLSpaceShipInstance x2 = new XLSpaceShipInstance();
		x2.setNextTurnBelongsToThisInstance(true);
		List<XLSpaceShipInstance> currentInstances = Arrays.asList(x1,x2);
		Mockito.when(g.getCurrenlyPlayingInstances()).thenReturn(currentInstances);
		
		XLSpaceShipInstance result = spaceService.findInstanceWhoseShipsAreToBeDestroyed(g);
		Assert.assertTrue("CurrentTurn of this player returned true", !result.isNextTurnBelongsToThisInstance());
		
	}
	
	@Test
	public void testWithNullInstance_findInstanceWhoseShipsAreToBeDestroyed(){
		XLSpaceShipInstance result = spaceService.findInstanceWhoseShipsAreToBeDestroyed(null);
		Assert.assertNull("Null Instance expected", result);
	}
	
	@Test
	public void testWithNegativeInput_findInstanceWhoseShipsAreToBeDestroyed(){
		Game g = Mockito.mock(Game.class);
		XLSpaceShipInstance x1 = new XLSpaceShipInstance();
		x1.setNextTurnBelongsToThisInstance(true);
		XLSpaceShipInstance x2 = new XLSpaceShipInstance();
		x2.setNextTurnBelongsToThisInstance(true);
		List<XLSpaceShipInstance> currentInstances = Arrays.asList(x1,x2);
		Mockito.when(g.getCurrenlyPlayingInstances()).thenReturn(currentInstances);
		
		XLSpaceShipInstance result = spaceService.findInstanceWhoseShipsAreToBeDestroyed(g);
		Assert.assertNotNull("Null Instance expected, as both instaances have turned ON NEXT Turn..Not Possbl conditon", result);
	}
	
	
	@Test
	public void testPositive_startAutoPilotMode(){
		Game g = dataGenerator.gameDataGenerator_fullRequestWith4ShipsPerPlayer();
		spaceService.startAutoPilotMode(g);
		boolean result = false;
		if((g.getCurrenlyPlayingInstances().get(0).getPosnToShipsMap().get("02").getShotsThisShipCanBear()==0) || 
				(g.getCurrenlyPlayingInstances().get(0).getPosnToShipsMap().get("ae").getShotsThisShipCanBear()==0) || 
				(g.getCurrenlyPlayingInstances().get(0).getPosnToShipsMap().get("8b").getShotsThisShipCanBear()==0) || 
				(g.getCurrenlyPlayingInstances().get(0).getPosnToShipsMap().get("9d").getShotsThisShipCanBear()==0) || 
				(g.getCurrenlyPlayingInstances().get(1).getPosnToShipsMap().get("1b").getShotsThisShipCanBear()==0)	||
				(g.getCurrenlyPlayingInstances().get(1).getPosnToShipsMap().get("92").getShotsThisShipCanBear()==0)	||
				(g.getCurrenlyPlayingInstances().get(1).getPosnToShipsMap().get("81").getShotsThisShipCanBear()==0)){
			result = true;
		}
		Assert.assertTrue("Count of Shots that any one of the above Ships can bear, must be ZERO..", result);
	}
	
	
	@Test
	public void testPositiveSimple_startAutoPilotMode(){
		Game g = dataGenerator.gameDataGenerator_fullRequestWith2ShipsPerPlayer();
		spaceService.startAutoPilotMode(g);
		boolean result = false;
		if((g.getCurrenlyPlayingInstances().get(0).getPosnToShipsMap().get("02").getShotsThisShipCanBear()==0) || 
				(g.getCurrenlyPlayingInstances().get(0).getPosnToShipsMap().get("ae").getShotsThisShipCanBear()==0) || 
				(g.getCurrenlyPlayingInstances().get(1).getPosnToShipsMap().get("1b").getShotsThisShipCanBear()==0) || 
				(g.getCurrenlyPlayingInstances().get(1).getPosnToShipsMap().get("92").getShotsThisShipCanBear()==0)){
			result = true;
		}
		Assert.assertTrue("Count of Shots that any one of the above Ships can bear, must be ZERO..", result);
	}
	
	
	@Test
	public void testPositiveSimple_fireSalvoOfShots(){
		RequestForFiringSalvoOfShots req = new RequestForFiringSalvoOfShots(Arrays.asList("02","d3"));
		Player p1 = new Player("Test1", "First Player");
		Player p2 = new Player("Test2", "Second Player");	
		
		Map<String,SpaceShip> posnToShipMapForP1 = new HashMap<String, SpaceShip>();
		posnToShipMapForP1.put("02", new WingerShip(2));
		posnToShipMapForP1.put("ae", new AClassShip(2));
		
		
		Map<String,SpaceShip> posnToShipMapForP2 = new HashMap<String, SpaceShip>();
		posnToShipMapForP2.put("1b", new WingerShip(2));
		posnToShipMapForP1.put("81", new SClassShip(2));
		
		XLSpaceShipInstance instanceWithCurrentTurn = new XLSpaceShipInstance();
		instanceWithCurrentTurn.setNextTurnBelongsToThisInstance(true);
		instanceWithCurrentTurn.setOwnerOfInstance(p1);
		instanceWithCurrentTurn.setPosnToShipsMap(posnToShipMapForP1);
		
		XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed = new XLSpaceShipInstance();
		instanceWhoseShipsAreToBeDestroyed.setNextTurnBelongsToThisInstance(false);
		instanceWhoseShipsAreToBeDestroyed.setOwnerOfInstance(p2);
		instanceWhoseShipsAreToBeDestroyed.setPosnToShipsMap(posnToShipMapForP2);
		
		ResponseForFiredSalvoOfShots res = spaceService.fireSalvoOfShotsAt(req, instanceWhoseShipsAreToBeDestroyed, instanceWithCurrentTurn, "standard");
		Assert.assertNotNull("Response of firing Salvo of Shots Must Not be Null..", res);
	}
	
	
	@Test
	public void testWhetherrStatusOfShipGettingChanged_fireSalvoOfShots(){
		RequestForFiringSalvoOfShots req = new RequestForFiringSalvoOfShots(Arrays.asList("1b","d3"));
		Player p1 = new Player("Test1", "First Player");
		Player p2 = new Player("Test2", "Second Player");	
		
		Map<String,SpaceShip> posnToShipMapForP1 = new HashMap<String, SpaceShip>();
		posnToShipMapForP1.put("02", new WingerShip(2));
		posnToShipMapForP1.put("ae", new AClassShip(2));
		
		
		Map<String,SpaceShip> posnToShipMapForP2 = new HashMap<String, SpaceShip>();
		posnToShipMapForP2.put("1b", new WingerShip(2));
		posnToShipMapForP2.put("81", new SClassShip(2));
		
		XLSpaceShipInstance instanceWithCurrentTurn = new XLSpaceShipInstance();
		instanceWithCurrentTurn.setNextTurnBelongsToThisInstance(true);
		instanceWithCurrentTurn.setOwnerOfInstance(p1);
		instanceWithCurrentTurn.setPosnToShipsMap(posnToShipMapForP1);
		
		XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed = new XLSpaceShipInstance();
		instanceWhoseShipsAreToBeDestroyed.setNextTurnBelongsToThisInstance(false);
		instanceWhoseShipsAreToBeDestroyed.setOwnerOfInstance(p2);
		instanceWhoseShipsAreToBeDestroyed.setPosnToShipsMap(posnToShipMapForP2);
		
		ResponseForFiredSalvoOfShots res = spaceService.fireSalvoOfShotsAt(req, instanceWhoseShipsAreToBeDestroyed, instanceWithCurrentTurn, "standard");
		
		Assert.assertEquals("Status of Ship must Changee...","HIT",res.getSalvo().get("1b"));
	}
	
	@Test
	public void testToKILLASHIP_fireSalvoOfShots(){
		RequestForFiringSalvoOfShots req = new RequestForFiringSalvoOfShots(Arrays.asList("1b","1b"));
		Player p1 = new Player("Test1", "First Player");
		Player p2 = new Player("Test2", "Second Player");	
		Map<String,SpaceShip> posnToShipMapForP1 = new HashMap<String, SpaceShip>();
		posnToShipMapForP1.put("02", new WingerShip(2));
		posnToShipMapForP1.put("ae", new AClassShip(2));
		
		Map<String,SpaceShip> posnToShipMapForP2 = new HashMap<String, SpaceShip>();
		posnToShipMapForP2.put("1b", new WingerShip(2));
		posnToShipMapForP2.put("81", new SClassShip(2));
		
		XLSpaceShipInstance instanceWithCurrentTurn = new XLSpaceShipInstance();
		instanceWithCurrentTurn.setNextTurnBelongsToThisInstance(true);
		instanceWithCurrentTurn.setOwnerOfInstance(p1);
		instanceWithCurrentTurn.setPosnToShipsMap(posnToShipMapForP1);
		
		XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed = new XLSpaceShipInstance();
		instanceWhoseShipsAreToBeDestroyed.setNextTurnBelongsToThisInstance(false);
		instanceWhoseShipsAreToBeDestroyed.setOwnerOfInstance(p2);
		instanceWhoseShipsAreToBeDestroyed.setPosnToShipsMap(posnToShipMapForP2);
		ResponseForFiredSalvoOfShots res = spaceService.fireSalvoOfShotsAt(req, instanceWhoseShipsAreToBeDestroyed, instanceWithCurrentTurn, "standard");
		Assert.assertEquals("Status of Ship must Changee...","KILL",res.getSalvo().get("1b"));
		Assert.assertEquals("Shots this ship can bear...",2,instanceWhoseShipsAreToBeDestroyed.getPosnToShipsMap().get("81").getShotsThisShipCanBear());
	}
	
	
}
