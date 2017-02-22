package com.aditya.xlabs.DataGenerator;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aditya.xlabs.APIType.ProtocolAPI;
import com.aditya.xlabs.Entity.AClassShip;
import com.aditya.xlabs.Entity.BClassShip;
import com.aditya.xlabs.Entity.Game;
import com.aditya.xlabs.Entity.Player;
import com.aditya.xlabs.Entity.SClassShip;
import com.aditya.xlabs.Entity.WingerShip;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.Request.RequestForNewGame;
import com.aditya.xlabs.factory.SpaceShip;

public class ManualDataObjectCreator {
	
	public Game gameDataGenerator_fullRequestWith2ShipsPerPlayer() {
		Game g = new Game();
		Player p1 = new Player("Test1", "First Player");
		Player p2 = new Player("Test2", "Second Player");	
		
		Map<String,SpaceShip> posnToShipMapForP1 = new HashMap<String, SpaceShip>();
		posnToShipMapForP1.put("02", new WingerShip(2));
		posnToShipMapForP1.put("ae", new AClassShip(2));
		
		
		Map<String,SpaceShip> posnToShipMapForP2 = new HashMap<String, SpaceShip>();
		posnToShipMapForP2.put("1b", new WingerShip(2));
		posnToShipMapForP1.put("81", new SClassShip(2));
		
		XLSpaceShipInstance x1 = new XLSpaceShipInstance();
		x1.setNextTurnBelongsToThisInstance(true);
		x1.setOwnerOfInstance(p1);
		x1.setPosnToShipsMap(posnToShipMapForP1);
		
		XLSpaceShipInstance x2 = new XLSpaceShipInstance();
		x2.setNextTurnBelongsToThisInstance(false);
		x2.setOwnerOfInstance(p2);
		x2.setPosnToShipsMap(posnToShipMapForP2);
		
		List<XLSpaceShipInstance> currentInstances = Arrays.asList(x1,x2);
		g.setGameID("test-1");
		g.setCurrenlyPlayingInstances(currentInstances);
		g.setStartTime(new Date());
		g.setRules("standard");
		return g;
	}
	
	public Game gameDataGenerator_fullRequestWith4ShipsPerPlayer() {
		Game g = new Game();
		Player p1 = new Player("Test1", "First Player");
		Player p2 = new Player("Test2", "Second Player");	
		
		Map<String,SpaceShip> posnToShipMapForP1 = new HashMap<String, SpaceShip>();
		posnToShipMapForP1.put("02", new WingerShip(2));
		posnToShipMapForP1.put("ae", new AClassShip(2));
		posnToShipMapForP1.put("8b", new BClassShip(2));
		posnToShipMapForP1.put("9d", new SClassShip(2));
		
		
		Map<String,SpaceShip> posnToShipMapForP2 = new HashMap<String, SpaceShip>();
		posnToShipMapForP2.put("1b", new WingerShip(2));
		posnToShipMapForP2.put("92", new AClassShip(2));
		posnToShipMapForP1.put("67", new BClassShip(2));
		posnToShipMapForP1.put("81", new SClassShip(2));
		
		XLSpaceShipInstance x1 = new XLSpaceShipInstance();
		x1.setNextTurnBelongsToThisInstance(true);
		x1.setOwnerOfInstance(p1);
		x1.setPosnToShipsMap(posnToShipMapForP1);
		x1.setIpAddressPlayingFrom("74.1.9.23");
		x1.setPortPlayingFrom("2349");
		
		XLSpaceShipInstance x2 = new XLSpaceShipInstance();
		x2.setNextTurnBelongsToThisInstance(false);
		x2.setOwnerOfInstance(p2);
		x2.setPosnToShipsMap(posnToShipMapForP2);
		x2.setIpAddressPlayingFrom("10.0.1.3");
		x2.setPortPlayingFrom("9810");
		
		List<XLSpaceShipInstance> currentInstances = Arrays.asList(x1,x2);
		g.setGameID("test-1");
		g.setCurrenlyPlayingInstances(currentInstances);
		g.setStartTime(new Date());
		g.setRules("standard");
		return g;
	}

	public RequestForNewGame requestForNewGameGenerator_FullyLoadedRequest() {
		RequestForNewGame req = new RequestForNewGame();
		req.setFull_name("Opponent");
		req.setUser_id("Xebia-Opponent");
		req.setRules("standard");
		ProtocolAPI p = new ProtocolAPI();
		p.setHostname("10.0.0.3");
		p.setPort("3435");
		req.setSpaceship_protocol(p);
		return req;
	}
	
	
	public RequestForNewGame requestForNewGameGenerator_NotContainsProtocolDetails() {
		RequestForNewGame req = new RequestForNewGame();
		req.setFull_name("Opponent");
		req.setUser_id("Xebia-Opponent");
		req.setRules("standard");
		return req;
	}
	
	public RequestForNewGame requestForNewGameGenerator_NotContainsOpponentPlayersDeetails(){
		RequestForNewGame req = new RequestForNewGame();
		req.setUser_id("Xebia-Opponent");
		req.setRules("standard");
		ProtocolAPI p = new ProtocolAPI();
		p.setHostname("10.0.0.3");
		p.setPort("3435");
		req.setSpaceship_protocol(p);
		return req;
	}
	
	public RequestForNewGame requestForNewGameGenerator_NOTContainsRuleType() {
		RequestForNewGame req = new RequestForNewGame();
		req.setFull_name("Opponent");
		req.setUser_id("Xebia-Opponent");
		ProtocolAPI p = new ProtocolAPI();
		p.setHostname("10.0.0.3");
		p.setPort("3435");
		req.setSpaceship_protocol(p);
		return req;
	}
	
}